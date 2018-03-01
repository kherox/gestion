from django import forms

from .models import Fournisseur

"""
    name     = models.CharField(max_length=150,verbose_name="Raison social")
    ville    = models.CharField(max_length=150,verbose_name="Ville")
    contact  = models.CharField(max_length=150,verbose_name="Telephone")
    cel      = models.CharField(max_length=20,verbose_name="Cel")
    email    = models.EmailField()
    created  = models.DateField(auto_now_add=True)
"""

class FournisseurForm(forms.ModelForm):
    name          = forms.CharField(max_length=150)
    ville         = forms.CharField(max_length=75)
    contact       = forms.CharField(max_length=75,required=False)
    cel           = forms.CharField(max_length=25,required=False)

    class Meta :
        model   = Fournisseur
        fields  = ("name","ville","contact","cel")

