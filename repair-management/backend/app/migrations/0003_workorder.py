# Generated by Django 3.0.5 on 2020-04-19 20:26

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('app', '0002_facility_active'),
    ]

    operations = [
        migrations.CreateModel(
            name='WorkOrder',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=250, verbose_name='Title')),
                ('description', models.TextField(blank=True, null=True, verbose_name='Description')),
                ('status', models.CharField(choices=[('NEW', 'New'), ('STARTED', 'Started'), ('COMPLETED', 'Completed'), ('CANCELLED', 'Cancelled')], default='NEW', max_length=10, verbose_name='Current status')),
                ('facility_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='app.Facility', verbose_name='Facility')),
            ],
        ),
    ]
