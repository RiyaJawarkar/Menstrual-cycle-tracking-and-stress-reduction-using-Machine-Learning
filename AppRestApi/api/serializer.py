from rest_framework.serializers import *
from .models import *
class UserSerializer(ModelSerializer):
    class Meta():
        model=UserModel
        fields='__all__'

class UserPeriodSerializer(ModelSerializer):
    class Meta():
        model=UserPeriodModel
        fields='__all__'

