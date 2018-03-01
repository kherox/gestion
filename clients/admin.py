# -*- coding: utf-8 -*-

from django.contrib import admin
from django.contrib.auth.models import  User

# Register your models here.

from .models import Client

from .forms import ClientsForm

from clientcontacts.models import ContactClient
from clientcontacts.forms  import ClientContactsForm

class ClientContactFormInline(admin.StackedInline) :
    model               = ContactClient
    form                =  ClientContactsForm
    verbose_name_plural = "Creation d'un contact"
    verbose_name        = "Contact client"
    extra               = 0
    



class ClientAdmin(admin.ModelAdmin) :
    inlines      = (ClientContactFormInline,)
    form         = ClientsForm
    list_display = ("clientcode", "clientraison", "clientactivite", "clientcontact", "clientemail")


    def save_model(self, request, obj, form, change) :
        obj.user = request.user
        super(ClientAdmin,self).save_model(request, obj, form, change)

    def formfield_for_foreignkey(self, db_field, request, **kwargs):
        # if db_field.name == "owner" :
        #     kwargs["queryset"] = Post.objects.filter(owner_id=owner_id=request.user.id)
        if db_field.name == "user":
            kwargs["queryset"] = User.objects.filter(id=request.user.id)
        return super(ClientAdmin,self).formfield_for_foreignkey(db_field, request, **kwargs)
    # def get_list_select_related(self,request) :
    #     qs    = super(FilterOrderAdmin,self).get_queryset(request)
    #     return qs.filter(owner_id=request.user.id)




admin.site.register(Client,ClientAdmin)
