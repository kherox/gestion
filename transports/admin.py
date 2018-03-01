from django.contrib import admin

# Register your models here.
from .models import Transport


class TransportAdmin(admin.ModelAdmin) :
    list_display = ("name","price","created")




admin.site.register(Transport,TransportAdmin)