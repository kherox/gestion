from django.contrib import admin

# Register your models here.

from .models import MainOeuvre


class MainOeuvreAdmin(admin.ModelAdmin) :
    list_display = ("name","montant","qualification","created")



admin.site.register(MainOeuvre,MainOeuvreAdmin)