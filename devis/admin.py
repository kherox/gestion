# -*- coding: utf-8 -*-

from django.contrib import admin
from django.shortcuts import render
from django.forms.models import model_to_dict
from django.db.models.fields.related import ManyToOneRel
from django.template.loader import get_template
from django.template import Context
from django.http import HttpResponse
import pdfkit


from django.conf import settings




# Register your models here.

from .models                    import  Devi , devis_config
from projects.models            import  Project 
from besoins.models             import  Besoin
from projecttransports.models   import  ProjectTransports 
from projectmaindoeuvres.models import  ProjectMainDoeuvre
from projectoutils.models       import  ProjectOutil
from rubriques.models           import Rubrique
from devisconfigs.models        import DevisConfig

#(BesoinInline,ProjectMainDoeuvreInline,ProjectOutilInline,ProjectTransportInline)

#Form imported here

from .forms                     import DevisForm
from besoins.forms              import BesoinForm


import os

def invoices(modeladmin,request,queryset) :
    val        = list(queryset.values("project"))
    id         = val[0]["project"]
    devis      = queryset.get()
    project    = Project.objects.get(id=id)
    
    rubriques  = Rubrique.objects.filter(project=project).all() 
    configs    = DevisConfig.objects.filter(project=project)

    besoins    = Besoin.objects.filter(project=project).all()
    
    outils      = ProjectOutil.objects.filter(project=project).all()
    mainoeuvre  = ProjectMainDoeuvre.objects.filter(project=project).all()
    transport   = ProjectTransports.objects.filter(project=project).all()
    devisconfig = devis_config.objects.filter(project=project)


    compact = False 

    for item in devisconfig:
        compact = item.compact
        break
    
        
    """
    SELECT * FROM devis_devi
    """
    total  = 0
    #p    = Devi.objects.raw("SELECT * FROM devis_devi")
    if besoins :
        montant =  0
        for bs in besoins:
            montant = montant + (bs.price * bs.quantity) 

    total = total + montant
    if transport :
        montant =  0
        for trans in transport :
            montant = montant + trans.montant
    total += montant
    
    if mainoeuvre :
        montant = 0
        for mdo in mainoeuvre :
            montant = montant + mdo.montant
    total += montant
    if outils :
        montant = 0
        for otl in outils :
            montant = montant + otl.montant
    total += montant

    data = {
        'besoins': besoins,
        'outils': outils,
        'transport': transport,
        'mainoeuvre': mainoeuvre,
        'devis': devis,
        'project': project,
        'total': total,
        'devisconfig': devisconfig,
        'compact': compact,
    }



    BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))



    


    template          = get_template("admin/invoices.html").render(data)
    # context           = Context(data)
    # html              = template.render(context)
    pdfkit.from_string(template,"uploads/output.pdf")
    pdf               = open(settings.BASE_DIR+settings.MEDIA_URL+"output.pdf")
    response          = HttpResponse(pdf.read(), content_type='application/pdf')

    response['Content-Disposition'] = 'attachment; filename=output.pdf'
    pdf.close()
    #os.remove("out.pdf")

    return response



        
 
    #Project.objects.get(id=1).values() #.select_related("activities","client") #queryset.values("project")
    #return render(request,"admin/invoices.html",data) 

invoices.short_description = "Generer une facture"


def preinvoice(modeladmin,request,queryset) :
    
    besoin_list = []
    val        = list(queryset.values("project"))
    id         = val[0]["project"]

    devis      = queryset.get()
    project    = Project.objects.get(id=id)

    besoins    = Besoin.objects.filter(project=project).all()
    outils     = ProjectOutil.objects.filter(project=project).all()

    mainoeuvre = ProjectMainDoeuvre.objects.filter(project=project).all()
    transport  = ProjectTransports.objects.filter(project=project).all()

    rubriques  = Rubrique.objects.filter(project=project).all()
    configs    = DevisConfig.objects.filter(project=project)

    

    # for besoin in besoins :
    #     besoin_list.append(BesoinForm(instance=besoin))

    
    return render(
        request,
        "admin/preinvoice.html",
        {
            "project"       : project.id ,
            "besoin_list"   : besoins ,
            "outils"        : outils,
            "mainoeuvre"    : mainoeuvre,
            "transports"    : transport,
            "rubriques"     : rubriques,
            "configs"       : configs,  
            "devis"         : devis
        }) 

preinvoice.short_description = "Pre-Validation"


def preview(modeladmin, request, queryset) :
    val          = list(queryset.values("project"))
    id           = val[0]["project"]
    devis        = queryset.get()

    #data = dict((f, f) for f in Project.objects.get(id=id)._meta.get_fields())

    project = Project.objects.get(id=id)._meta.get_fields()
    for p in project :
        if isinstance(p, ManyToOneRel):
            try:
                #m = p.field.model()
                m = p.related_model()
                print(m)
                # for field in m._meta.fields :
                #     print(field)
            except Exception as e:
                pass
           
        # if p.many_to_one or p.is_relation:
        #     for val in p.__dict__ :
        #         if val == "model" :
        #             m = p.model()
        #             print(type(m))
               
        #break


    return render(request,"admin/preview.html",{
        "project" : project,
        "devis"  : devis
      }) 

preview.short_description   = "Previsualisé le devis"


# def generatePdf(modeladmin, request, queryset) :
#     template = get_template("admin/invoices.html")


# generatePdf.short_description = "Generer le PDF"


class DeviAdmin(admin.ModelAdmin):
    
    actions = [invoices]
    form    = DevisForm


from .models import devis_config

admin.site.register(Devi,DeviAdmin)
#admin.site.register(devis_config)
