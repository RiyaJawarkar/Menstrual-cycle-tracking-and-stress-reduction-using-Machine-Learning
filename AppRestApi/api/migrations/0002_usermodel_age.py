# Generated by Django 5.0.6 on 2025-03-04 10:06

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='usermodel',
            name='age',
            field=models.IntegerField(default=0),
        ),
    ]
