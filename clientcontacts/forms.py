from django import forms

from .models import ContactClient

"""
    name       = models.CharField(max_length=150)
    fonction   = models.CharField(max_length=150)
    telephone  = models.CharField(max_length=15)
    mobile     = models.CharField(max_length=15)
    email      = models.EmailField(max_length=150)
    client     = models.ForeignKey(Client,on_delete=models.SET_DEFAULT)
"""


class ClientContactsForm(forms.ModelForm):
    name            = forms.CharField(max_length=150,label="Nom et Prenoms")
    fonction        = forms.CharField(max_length=50,label="Fonction",required=False)
    telephone       = forms.CharField(max_length=150, label="Telephone", required=False)
    mobile          = forms.CharField(max_length=15, label="Cel", required=False)
    email           = forms.CharField(max_length=25, label="Email", required=False)

    class Meta :
        model = ContactClient
        fields = ("name","fonction","telephone","mobile","email","client")
