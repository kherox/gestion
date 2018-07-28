from django import forms

from .models import Besoin

"""
    product           = models.ForeignKey(Product,on_delete=models.PROTECT)
    quantity          = models.IntegerField(verbose_name="Le quantité")
    project           = models.ForeignKey(Project,on_delete=models.PROTECT)
    price             = models.IntegerField(verbose_name="Le Prix")
    marge             = models.FloatField(verbose_name=None,blank=False,null=True,default=1.0,editable=True)
    remise            = models.FloatField(verbose_name=None,blank=False,null=True,default=1.0,editable=True)

"""

class BesoinForm(forms.ModelForm) :
    marge             = forms.FloatField(label="Marge",required=False)
    remise            = forms.FloatField(label="Remise",required=False)


    class Meta :
        model    = Besoin
        fields   = ("product","quantity","project","price","marge","remise")

