from django.contrib import admin

# Register your models here.
from .models import Fournisseur

class FournisseurAdmin(admin.ModelAdmin) :
    list_display = ("name","contact","email","ville")


admin.site.register(Fournisseur,FournisseurAdmin)