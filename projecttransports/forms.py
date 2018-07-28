from django import forms


from .models import ProjectTransports



"""
    transport       = models.ForeignKey(Transport,on_delete=models.PROTECT)
    coutkm          = models.IntegerField(verbose_name="Cout au KM",editable=False,null=True)
    distance        = models.IntegerField(verbose_name="Distance KM")
    coefficient     = models.IntegerField(verbose_name="Coefficient %")
    montant         = models.IntegerField(verbose_name="Montant")
    project         = models.ForeignKey(Project,on_delete=models.PROTECT)
"""

class ProjectTransportsForm(forms.ModelForm) :
    coefficient      = forms.IntegerField(label="Coefficient en %",required=False)
    montant          = forms.IntegerField(label="Montant",required=False)

    class Meta :
        model  = ProjectTransports
        fields = ("transport","distance","coefficient","montant","project")
        