# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-02-18 22:49
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('clients', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='ContactClient',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=150)),
                ('fonction', models.CharField(max_length=150)),
                ('telephone', models.CharField(max_length=15)),
                ('mobile', models.CharField(max_length=15)),
                ('email', models.EmailField(max_length=150)),
                ('client', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='clients.Client')),
            ],
        ),
    ]
