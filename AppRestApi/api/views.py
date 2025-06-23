from django.shortcuts import render
from rest_framework.decorators import *
from rest_framework.response import *
from rest_framework.generics import *
from rest_framework import parsers
from .models import *
from .serializer import *
from rest_framework.filters import *
from django_filters.rest_framework import DjangoFilterBackend
from .Prediction import *
from datetime import *
# Create your views here.
@api_view(['GET'])
def index(request):
    data="Connected..."
    return Response(data)


class CreateUserView(CreateAPIView):
    queryset=UserModel.objects.all()
    serializer_class=UserSerializer

class ListUserView(ListAPIView):
    queryset=UserModel.objects.all()
    serializer_class=UserSerializer

class OneUserView(RetrieveAPIView):
    queryset=UserModel.objects.all()
    serializer_class=UserSerializer

class UpdateUserView(UpdateAPIView):
    queryset=UserModel.objects.all()
    serializer_class=UserSerializer

class CreateUserPeriodView(CreateAPIView):
    queryset=UserPeriodModel.objects.all()
    serializer_class=UserPeriodSerializer

class ListUserPeriodView(ListAPIView):
    queryset=UserPeriodModel.objects.all()
    serializer_class=UserPeriodSerializer
    filter_backends = [DjangoFilterBackend, SearchFilter, OrderingFilter]
    filterset_fields = ['userid']
    #search_fields = ['userid']
    ordering_fields = ['periodDate']
    ordering = ['-periodDate']

@api_view(['GET'])
def getPredictionByUserId(request,id):
    userPeriodModelRows=UserPeriodModel.objects.filter(userid=id)
    userModel=UserModel.objects.get(id=id)
    print(userPeriodModelRows)
    if len(userPeriodModelRows)>0:
        cycle_length=0
        period_length=0
        for row in userPeriodModelRows:
            age=userModel.age
            cycle_length+=row.periodCycle
            period_length+=row.periodLength
            stress_level=5
            match row.periodStress :
                case 'Low':stress_level=3 
                case 'Medium':stress_level=6 
                case 'High':stress_level=9
        average_cycle_length=cycle_length/len(userPeriodModelRows)
        average_period_length=period_length/len(userPeriodModelRows)
        result=predict_next_period(age,average_cycle_length,average_period_length,stress_level)
        print(row.periodDate)
        LastPeriodDate = datetime.strptime(str(row.periodDate), '%Y-%m-%d')
        # Add days
        NextPeriodDate = LastPeriodDate + timedelta(days=int(result))
        NextPeriodDateStr=NextPeriodDate.strftime('%Y-%m-%d')
        # Print the new date
        print(LastPeriodDate)
        # Get the current date
        CurrentDate = datetime.today()
        # Calculate difference in days
        DifferenceInDays = (NextPeriodDate-CurrentDate).days
        DifferenceInCycle = (NextPeriodDate-LastPeriodDate).days

        data={"PeriodPrediction":result,"PeriodDate":NextPeriodDateStr,"nextperiodLength":round(average_period_length),"nextPeriodindays":DifferenceInDays,"PeriodCycle":DifferenceInCycle}
        return Response(data)

    data={"PeriodPrediction":0,"PeriodDate":"Start Prediction","nextperiodLength":0,"nextPeriodindays":0,"PeriodCycle":0}
    return Response(data)
