from django import forms

from .models import ProjectOutil

"""
    outil         = models.ForeignKey(Outil,on_delete=models.PROTECT)
    valeur        = models.IntegerField(verbose_name="Valeur",editable=False)
    quantite      = models.IntegerField(verbose_name="Quantité necessaire")
    duree         = models.IntegerField(verbose_name="Nombre de jours")
    montant       = models.IntegerField(verbose_name="Montant",null=True,blank=True)
    project       = models.ForeignKey(Project,on_delete=models.PROTECT)
"""

class ProjectOutilForm(forms.ModelForm) :
    montant       = forms.IntegerField(label="Montant",required=False)
    duree         = forms.IntegerField(label="Nombre de jours",required=False)

    class Meta :
        model   = ProjectOutil
        fields  = ("outil","quantite","duree","montant","project")