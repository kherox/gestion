from django.db import models

from projects.models import Project
from mainoeuvres.models import MainOeuvre
# Create your models here.

class ProjectMainDoeuvre(models.Model) :
    mainoeuvre       = models.ForeignKey(MainOeuvre,on_delete=models.PROTECT,verbose_name="Specialité")
    tauxhoraire      = models.IntegerField(verbose_name="Taux Horaire",editable=False,null=True)
    nombreheure      = models.IntegerField(verbose_name="Nombre d'Heure")
    coefficient      = models.IntegerField(verbose_name="Coefficient d'ajustement")
    nombrepersonnes  = models.IntegerField(verbose_name="Nombre de personnes")
    montant          = models.IntegerField(verbose_name="Montant",null=True,blank=True)
    project          = models.ForeignKey(Project,on_delete=models.PROTECT)


from django.db.models import signals

def projectmaindeouvre_pre_save(signal,instance,sender,**kwagrs) :
    if instance.mainoeuvre :
        taux                        = getattr(instance.mainoeuvre,"montant",None)
        if taux is not None :
            instance.tauxhoraire     = int(taux)
            montant                  = instance.tauxhoraire * instance.nombreheure * instance.nombrepersonnes *  (instance.coefficient / 100)
            instance.montant         = montant
        

signals.pre_save.connect(projectmaindeouvre_pre_save,sender=ProjectMainDoeuvre)