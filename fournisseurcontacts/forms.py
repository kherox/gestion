from django import forms

from .models import FournisseurContact

"""
    name        = models.CharField(max_length=150)
    fonction    = models.CharField(max_length=50)
    cel         = models.CharField(max_length=20)
    email       = models.CharField(max_length=50)
"""


class FournisseurContactForm(forms.ModelForm) :
    name      = forms.CharField(label="Nom et Prenom")
    fonction  = forms.CharField(label="Fonction", required=False)
    cel       = forms.CharField(label="Cel", required=False)
    email     = forms.CharField(label="Email",required=False)

    class Meta :
        model   = FournisseurContact
        fields  = ("name","fonction","cel","email")
