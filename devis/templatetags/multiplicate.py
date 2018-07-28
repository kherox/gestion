from django import template

register = template.Library()

@register.filter(name="somme")
def somme(value,args) :
    if value > 0:
        return value + args
    else :
        return args

@register.filter(name='multiple')
def multiple(value,arg) :
    return value * arg

@register.filter(name='solde')
def solde(value) :
    return 100 - value

@register.filter(name='outiltotal')
def outiltotal(values) :
    montant =  0 
    for val in values :
        montant = montant + int(val.montant)
    return montant

@register.filter(name='transportotal')
def transportotal(values) :
    montant =  0 
    for val in values :
        montant = montant + int(val.montant)
    return montant

@register.filter(name='mainoeuvretotal')
def mainoeuvretotal(values) :
    montant =  0 
    for val in values :
        montant = montant + int(val.montant)
    return montant


@register.filter(name='tva')
def tva(values) :
    return values * 0.18 


@register.filter(name='escompte')
def escompte(values,avance) :
    """
     ON calcul les escompte a partir des l'avance et du montant global
    """
    escompte = (values + (values * 0.18)) - (values - (values * avance))
    return escompte


@register.filter(name='acompte')
def acompte(values) :
    return values

@register.filter(name='ttc')
def ttc(values) :
    # a calculer
    tva = values * 0.18 
    return values + tva 

@register.filter(name="htax")
def htax(values):
    return values


@register.filter(name='total')
def total(besoins,transport,maindoeuvre,outils) :
    total = 0
    if besoins :
        for besoin in besoins :
            montant = besoin.price * besoin.quantity
            total   = total + montant
    if transport :
        montant =  0
        for trans in transport :
            montant = montant + trans.montant
        total += montant
    
    if maindoeuvre :
        montant = 0
        for mdo in maindoeuvre :
            montant = montant + mdo.montant
        total += montant
    if outils :
        montant = 0
        for otl in outils :
            montant = montant + otl.montant
        total += montant
    return total
            

            

