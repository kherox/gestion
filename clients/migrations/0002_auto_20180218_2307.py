# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-02-18 23:07
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('clients', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='client',
            name='clientcity',
            field=models.CharField(max_length=150),
        ),
    ]
