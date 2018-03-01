from django.contrib import admin

# Register your models here.
from .models import Fournisseur
from .forms  import FournisseurForm

from fournisseurcontacts.models import FournisseurContact
from fournisseurcontacts.forms  import FournisseurContactForm

class FournisseurContactInline(admin.StackedInline) :
    model = FournisseurContact
    form  = FournisseurContactForm
    verbose_name_plural = "Creation du contact"
    verbose_name        = "Fournisseur"
    extra = 0

class FournisseurAdmin(admin.ModelAdmin) :
    inlines      = (FournisseurContactInline,)
    form         = FournisseurForm
    list_display = ("name","contact","ville")


admin.site.register(Fournisseur,FournisseurAdmin)
