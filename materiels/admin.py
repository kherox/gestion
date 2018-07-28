from django.contrib import admin

# Register your models here.
from .models import Materiel


class MaterielAdmin(admin.ModelAdmin) :
    list_display = ("name","created")



admin.site.register(Materiel,MaterielAdmin)