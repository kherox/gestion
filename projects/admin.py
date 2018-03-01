# -*- coding: utf-8 -*-
from django.contrib import admin

# Register your models here.

from .models                    import Project
from besoins.models             import Besoin
from projectmaindoeuvres.models import ProjectMainDoeuvre
from projectoutils.models       import ProjectOutil
from projecttransports.models   import ProjectTransports
from rubriques.models           import Rubrique
from devisconfigs.models        import DevisConfig

#form here

from .forms                      import ProjectForm
from projectoutils.forms         import ProjectOutilForm
from besoins.forms               import BesoinForm
from projectmaindoeuvres.forms   import ProjectMainDoeuvreForm
from projecttransports.forms     import ProjectTransportsForm

class BesoinInline(admin.StackedInline) :
    model               = Besoin
    form                = BesoinForm
    verbose_name_plural = "Creation des besoins"
    verbose_name        = "Liste des besoins"
    extra               = 0

    class BesoinAdmin(admin.ModelAdmin) :
        def get_queryset(self, request):
            qs = super().get_queryset(request)
            if request.rubrique:
                return qs.filter(project=request.project)
            return qs
    

class ProjectMainDoeuvreInline(admin.StackedInline) :
    model               = ProjectMainDoeuvre
    form                = ProjectMainDoeuvreForm
    verbose_name_plural = "Calcul du cout de la main d'oeuvre"
    verbose_name        = "Cout d'un element"
    extra               = 0

class ProjectOutilInline(admin.StackedInline) :
    model               = ProjectOutil 
    form                = ProjectOutilForm
    verbose_name        = "Calcul valeur equipements"
    verbose_name_plural = "Mise a disposition des outils"
    extra                = 0
class ProjectTransportInline(admin.StackedInline) :
    model               = ProjectTransports 
    form                = ProjectTransportsForm
    verbose_name        = "Cout d'un transport"
    verbose_name_plural = "Transport"
    extra                = 0

class RubriqueInline(admin.StackedInline) :
    model               = Rubrique
    verbose_name        = "Rubrique"
    verbose_name_plural = "Rubriques"
    extra               = 0

    

class DevisConfigInline(admin.StackedInline) :
    model               = DevisConfig
    verbose_name        = "Configuration"
    extra               = 0

class ProjectsAdmin(admin.ModelAdmin) :
    form              = ProjectForm
    inlines           = (RubriqueInline,BesoinInline,ProjectMainDoeuvreInline,ProjectOutilInline,ProjectTransportInline,DevisConfigInline)
    list_display      = ("name","activities","client","ville","user","created")
    search_fields     = ()

    def save_model(self, request, obj, form, change) :
        obj.user = request.user
        super(ProjectsAdmin, self).save_model(request, obj, form, change)

   
   
    class Media :
        js = ("jecompare.js",)



admin.site.register(Project,ProjectsAdmin)
