from django.db.models import * 

# Create your models here.
class UserModel(Model):
    id=AutoField(primary_key=True)
    name=CharField(max_length=255)
    mobile=CharField(max_length=10)
    age=IntegerField(default=0)
    password=CharField(max_length=100)

class UserPeriodModel(Model):
    id=AutoField(primary_key=True)
    userid=ForeignKey(UserModel,CASCADE)
    periodCycle=IntegerField()
    periodLength=IntegerField()
    periodDate=DateField()
    periodStress=CharField(max_length=200)

    
