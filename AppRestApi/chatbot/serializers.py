from rest_framework import serializers
from .models import ChatSession, Message

class MessageSerializer(serializers.ModelSerializer):
    """
    Serializer for Message model.
    """
    class Meta:
        model = Message
        fields = ['id', 'content', 'is_bot', 'timestamp']
        read_only_fields = ['id', 'is_bot', 'timestamp']


class ChatSessionSerializer(serializers.ModelSerializer):
    """
    Serializer for ChatSession model.
    """
    messages = MessageSerializer(many=True, read_only=True)
    
    class Meta:
        model = ChatSession
        fields = ['session_id', 'user_name', 'created_at', 'last_interaction', 'is_active', 'messages']
        read_only_fields = ['session_id', 'created_at', 'last_interaction']


class ChatMessageRequestSerializer(serializers.Serializer):
    """
    Serializer for chat message request.
    """
    session_id = serializers.UUIDField(required=False)
    message = serializers.CharField(required=True)


class ChatMessageResponseSerializer(serializers.Serializer):
    """
    Serializer for chat message response.
    """
    session_id = serializers.UUIDField()
    response = serializers.CharField()
    end_session = serializers.BooleanField(default=False)
