from django.contrib import admin

# Register your models here.

from .models import Product

class ProductAdmin(admin.ModelAdmin) :
    
    list_display   = ("reference","name","quantity","price","fournisseur","type_materiel")
    #list_filter    = (("fournisseur",admin.RelatedOnlyFieldListFilter),"type_materiel")
    search_fields  = ("name","reference")


admin.site.register(Product,ProductAdmin)