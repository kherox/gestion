from django.contrib import admin

# Register your models here.

from .models import Product
from .forms  import ProductForm



class ProductAdmin(admin.ModelAdmin) :
    
    form           = ProductForm
    
    list_display   = ("name","quantity","price","fournisseur","type_materiel")
    #list_filter    = (("fournisseur",admin.RelatedOnlyFieldListFilter),"type_materiel")
    search_fields  = ("name",)

    def save_model(self, request, obj, form, change) :
        obj.online = False 
        super(ProductAdmin, self).save_model(request, obj, form, change)



admin.site.register(Product,ProductAdmin)
