from django.contrib import admin
from django.shortcuts import render


# Register your models here.

from .models                    import  Devi
from projects.models            import  Project 
from besoins.models             import  Besoin
from projecttransports.models   import  ProjectTransports 
from projectmaindoeuvres.models import  ProjectMainDoeuvre
from projectoutils.models       import  ProjectOutil

# (BesoinInline,ProjectMainDoeuvreInline,ProjectOutilInline,ProjectTransportInline)

#Form imported here

from .forms                     import DevisForm
from besoins.forms              import BesoinForm

def invoices(modeladmin,request,queryset) :
    val        = list(queryset.values("project"))
    id         = val[0]["project"]
    devis      = queryset.get()
    project    = Project.objects.get(id=id)
    besoins    = Besoin.objects.filter(project=project).all()
    outils     = ProjectOutil.objects.filter(project=project).all()
    mainoeuvre = ProjectMainDoeuvre.objects.filter(project=project).all()
    transport  = ProjectTransports.objects.filter(project=project).all()

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

        
        
    #Project.objects.get(id=1).values() #.select_related("activities","client") #queryset.values("project")
    return render(request,"admin/invoices.html",
            {
                'besoins'     : besoins,
                'outils'      : outils,
                'transport'  : transport,
                'mainoeuvre'  : mainoeuvre,
                'devis'       : devis,
                'project'     : project,
                'total'       : total,
            }) 

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

    for besoin in besoins :
        besoin_list.append(BesoinForm(instance=besoin))




    
    return render(request,"admin/preinvoice.html",{"besoin_list":besoin_list}) 

preinvoice.short_description = "Pre-Validation"


class DeviAdmin(admin.ModelAdmin):
    actions = [invoices,preinvoice]
    form    = DevisForm


admin.site.register(Devi,DeviAdmin)