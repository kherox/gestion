from django.contrib import admin

from .models import Outil

# Register your models here.

class OutilAdmin(admin.ModelAdmin) :
    list_display = ("name","price","created")


admin.site.register(Outil,OutilAdmin)