from django.contrib import admin

# Register your models here.

from .models import Client

class ClientAdmin(admin.ModelAdmin) :
    list_display = ("clientcode","clientname","clientraison","clientcontact","clientactivite") 


admin.site.register(Client,ClientAdmin)