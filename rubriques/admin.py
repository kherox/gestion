# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin

# Register your models here.

from .models import Rubrique

# class RubriqueAdminModel(admin.ModelAdmin) :
#     # def get_queryset(self,request) :
#     #     qs   = super().get_queryset(request)
#     #     if request.project :
#     #         return qs.filter(project=request.project)
#     #     return qs   
#     def formfield_for_foreignkey(self, db_field, request, **kwargs):
#         print("*********************")
#         print(db_field)
#         print("*********************")
#         #if db_field.name == "rubrique":
#         kwargs["queryset"] = Rubrique.objects.filter(project=request.project)
#         return super(ProjectsAdmin, self).formfield_for_foreignkey(db_field, request, **kwargs)
    



# admin.site.register(Rubrique,RubriqueAdminModel)