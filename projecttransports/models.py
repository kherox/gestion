from django.db import models

# Create your models here.


from projects.models import Project
from transports.models import Transport




class ProjectTransports(models.Model) :
    transport       = models.ForeignKey(Transport,on_delete=models.PROTECT)
    coutkm          = models.IntegerField(verbose_name="Cout au KM",editable=False,null=True)
    distance        = models.IntegerField(verbose_name="Distance KM")
    coefficient     = models.IntegerField(verbose_name="Coefficient %")
    montant         = models.IntegerField(verbose_name="Montant")
    project         = models.ForeignKey(Project,on_delete=models.PROTECT)


from django.db.models import signals

def projectransport_pre_save(signal,instance,sender,**kwargs) :
    if instance.transport :
        val                 = getattr(instance.transport,"price",None)
        if val is not None :
            instance.coutkm = int(val)
            montant         = instance.coutkm * instance.distance  * (instance.coefficient / 100) 
            instance.montant=montant

signals.pre_save.connect(projectransport_pre_save,sender=ProjectTransports)