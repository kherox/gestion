# -*- coding: utf-8 -*-

from django import forms

from .models import Client

"""
    clientcode      = models.CharField(max_length=25)
    clientname      = models.CharField(max_length=150)
    clientraison    = models.CharField(max_length=150)
    clientcontact   = models.CharField(max_length=15)
    clientcity      = models.CharField(max_length=15)
    clientcountry   = models.CharField(max_length=15)
    clientfax       = models.CharField(max_length=15)
    clientemail     = models.EmailField(max_length=25)
    clientreglement = models.CharField(max_length=50)
    clientactivite  = models.ForeignKey(Activitie,on_delete=models.CASCADE)
    created         = models.DateField(auto_now_add=True)
    user            = models.ForeignKey(User,on_delete=models.CASCADE)

    clientname       = forms.CharField(max_length=150,verbose_name="Nom du client")
"""

class ClientsForm(forms.ModelForm) :
    clientcode       = forms.CharField(max_length=150,required=False,label="Code du client")
    clientraison     = forms.CharField(max_length=150,label="Raison Sociale")
    clientcity       = forms.CharField(max_length=150,label="Addresse Geographique",required=False)
    clientcontact    = forms.CharField(max_length=150,label="Telephone")
    clientfax        = forms.CharField(max_length=150, label="Fax", required=False)
    clientemail      = forms.CharField(max_length=150, label="Email", required=False)

    class Meta :
        model    = Client
        fields = ("clientcode", "clientraison", "clientcity",
                  "clientcontact", "clientfax", "clientemail", "clientactivite")
