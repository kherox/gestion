# -*- coding: utf-8 -*-
from django import forms

from .models import Product

"""
reference     = models.CharField(max_length=25)
    name          = models.CharField(max_length=25)
    quantity      = models.IntegerField()
    price         = models.IntegerField()
    fournisseur   = models.ForeignKey(Fournisseur,on_delete=models.CASCADE)
    type_materiel = models.ForeignKey(Materiel,on_delete=models.CASCADE)
    online        = models.BooleanField()
    created       = models.DateField(auto_now_add=True)
"""

class ProductForm(forms.ModelForm) :
    
    class Meta :
        model   = Product
        fields  = ("name","quantity","price","fournisseur","type_materiel")
