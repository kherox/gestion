from django.contrib import admin

# Register your models here.

from .models                    import Project
from besoins.models             import Besoin
from projectmaindoeuvres.models import ProjectMainDoeuvre
from projectoutils.models       import ProjectOutil
from projecttransports.models   import ProjectTransports

#form here

from .forms                      import ProjectForm
from projectoutils.forms         import ProjectOutilForm
from besoins.forms               import BesoinForm
from projectmaindoeuvres.forms   import ProjectMainDoeuvreForm
from projecttransports.forms     import ProjectTransportsForm

class BesoinInline(admin.StackedInline) :
    model = Besoin
    form  = BesoinForm
    verbose_name_plural = "Creation des besoins"
    verbose_name = "Liste des besoins"
    extra        = 0

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
class ProjectsAdmin(admin.ModelAdmin) :
    form              = ProjectForm
    inlines           = (BesoinInline,ProjectMainDoeuvreInline,ProjectOutilInline,ProjectTransportInline)
    list_display      = ("name","activities","client","ville","user","created")
    search_fields     = ()



admin.site.register(Project,ProjectsAdmin)