from django import forms

from .models import ProjectMainDoeuvre


"""
    mainoeuvre       = models.ForeignKey(MainOeuvre,on_delete=models.PROTECT,verbose_name="Specialité")
    tauxhoraire      = models.IntegerField(verbose_name="Taux Horaire",editable=False,null=True)
    nombreheure      = models.IntegerField(verbose_name="Nombre d'Heure")
    coefficient      = models.IntegerField(verbose_name="Coefficient d'ajustement")
    nombrepersonnes  = models.IntegerField(verbose_name="Nombre de personnes")
    montant          = models.IntegerField(verbose_name="Montant",null=True,blank=True)
    project          = models.ForeignKey(Project,on_delete=models.PROTECT)
"""

class ProjectMainDoeuvreForm(forms.ModelForm) :
    coefficient      = forms.IntegerField(label="Coefficient d'ajustement",required=False)
    montant          = forms.IntegerField(label="Montant",required=False)

    class Meta :
        model  = ProjectMainDoeuvre
        fields = ("mainoeuvre","nombreheure","coefficient","nombrepersonnes","montant","project")
         