from django.db import models

# Create your models here.
import uuid

class ChatSession(models.Model):
    """
    Model to store chat session information.
    """
    session_id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    user_name = models.CharField(max_length=100, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    last_interaction = models.DateTimeField(auto_now=True)
    is_active = models.BooleanField(default=True)

    def __str__(self):
        return f"Session {self.session_id} - {self.user_name or 'Unnamed'}"


class Message(models.Model):
    """
    Model to store chat messages.
    """
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    session = models.ForeignKey(ChatSession, on_delete=models.CASCADE, related_name='messages')
    content = models.TextField()
    is_bot = models.BooleanField(default=False)
    timestamp = models.DateTimeField(auto_now_add=True)

    class Meta:
        ordering = ['timestamp']

    def __str__(self):
        sender = "Bot" if self.is_bot else "User"
        return f"{sender}: {self.content[:50]}"

