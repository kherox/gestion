from django import forms

from .models import Project


"""
    name            = models.CharField(max_length=250)
    activities      = models.ForeignKey(Activitie,on_delete=models.PROTECT)
    client          = models.ForeignKey(Client,on_delete=models.PROTECT)
    personneabout   = models.CharField(max_length=150)
    ville           = models.CharField(max_length=25)
    description     = models.TextField()
    created         = models.DateField(auto_now_add=True)
    marge           = models.FloatField(verbose_name=None,blank=False,null=True,default=1.0,editable=True)
    remise          = models.FloatField(verbose_name=None,blank=False,null=True,default=1.0,editable=True)
    user            = models.ForeignKey(User, on_delete=models.PROTECT)

"""

class ProjectForm(forms.ModelForm) :
    remise        = forms.FloatField(max_value=None,min_value=None,label="Remise",required=False)
    marge         = forms.FloatField(max_value=None,min_value=None,label="Marge",required=False)
    personneabout = forms.CharField(max_length=None,label="A l'attention de",required=False)

    class Meta :
        model  = Project
        fields = ("name","activities","client","personneabout","marge","remise","ville","description")
