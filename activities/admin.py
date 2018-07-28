from django.contrib import admin

# Register your models here.

from .models import Activitie

class ActivitieAdmin(admin.ModelAdmin) :
    list_display = ("name","abreviation","created")


admin.site.register(Activitie,ActivitieAdmin)