# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-02-18 22:49
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('rubriques', '0001_initial'),
        ('besoins', '0002_auto_20180115_0857'),
    ]

    operations = [
        migrations.AddField(
            model_name='besoin',
            name='rubrique',
            field=models.ForeignKey(default=None, on_delete=django.db.models.deletion.PROTECT, to='rubriques.Rubrique'),
        ),
    ]