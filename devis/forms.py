from django import forms

from .models import Devi

"""
    project        = models.ForeignKey(Project,on_delete=models.PROTECT)
    numeroproforma = models.CharField(max_length=150,verbose_name="Numero de la proforma : ")
    delailivraison = models.CharField(max_length=150,verbose_name="Delai de livraison : ")
    finvalidate    = models.DateField(verbose_name="Date de fin de validité : ")
    datedemande    = models.DateField(max_length=150,verbose_name="Date de demande : ")
    avance         = models.IntegerField(verbose_name="Avance en % : ")

"""

class DevisForm(forms.ModelForm) :
    numeroproforma  = forms.CharField(widget=forms.HiddenInput(),required=False)
    delailivraison  = forms.CharField(max_length=150,label="Delai de livraison ")
    finvalidate     = forms.DateField(label="Date de fin de validité  ")
    datedemande     = forms.DateField(label="Date de demande ")
    avance          = forms.IntegerField(label="Avance en %",required=False)

    class Meta :
        model = Devi
        fields = ("project","numeroproforma","delailivraison","finvalidate","datedemande","avance")