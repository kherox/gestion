from django.contrib import admin

# Register your models here.

from .models import Besoin
from rubriques.models import Rubrique

# class BesoinAdmin(admin.ModelAdmin) :
    
#     def formfield_for_foreignkey(self, db_field, request, **kwargs):
#         print("*********************")
#         print(db_field)
#         print("*********************")
#         if db_field.name == "rubrique":
#             kwargs["queryset"] = Rubrique.objects.filter(project=request.project)
#         return super(BesoinAdmin, self).formfield_for_foreignkey(db_field, request, **kwargs)










admin.site.register(Besoin)
