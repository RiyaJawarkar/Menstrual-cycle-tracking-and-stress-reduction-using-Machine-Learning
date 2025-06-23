import random
from datetime import datetime

class PeriodStressReductionBot:
    def __init__(self, user_name=""):
        self.user_name = user_name or "there"
        
        # Knowledge base for period stress management
        self.stress_tips = [
            "Try gentle yoga poses like child's pose or legs up the wall to reduce cramping.",
            "Apply a heating pad to your lower abdomen to help relieve menstrual cramps.",
            "Stay hydrated! Drinking plenty of water can help reduce bloating and cramps.",
            "Certain herbal teas like chamomile, ginger, or peppermint may help soothe period discomfort.",
            "Practice deep breathing exercises - inhale for 4 counts, hold for 4, exhale for 6.",
            "Light exercise like walking can increase endorphins and reduce period pain.",
            "Track your cycle in a journal or app to better anticipate and prepare for symptoms.",
            "Reduce caffeine and salt intake during your period to minimize bloating and breast tenderness.",
            "Consider trying mindfulness meditation to help manage pain perception.",
            "Ensure you're getting enough magnesium-rich foods like dark chocolate, nuts, and leafy greens."
        ]
        
        # YouTube video suggestions organized by symptom/topic
        self.youtube_suggestions = {
            "yoga": [
                {"title": "10-Minute Yoga for Period Pain Relief", "url": "<a href='https://www.youtube.com/watch?v=r6hyWhp5rQg'> Yoga for Period Cramps - 10 min Relief from Menstrual Pain, Aches & PMS </a>"},
                {"title": "Gentle Yoga Flow for Menstrual Cramps", "url": "<a href='https://www.youtube.com/watch?v=eZdwBl1yu14'>Yoga for Menstrual Cramps - Gentle Yoga for Your Period </a>"},
                {"title": "Restorative Yoga Poses for Period Discomfort","url": "<a href='https://www.youtube.com/watch?v=4JaCcp39iVI'>Yoga for Cramps and PMS | 20-Minute Home Yoga </a>"}
            ],
            "meditation": [
                {"title": "Guided Meditation for PMS and Period Pain", "url": "<a href='https://www.youtube.com/watch?v=sxRXiEDHtRU'>Soothe Your Period Pain With This Guided Meditation By A Clinical Psychologist (PMS Meditation)</a>"},
                {"title": "5-Minute Breathing Exercise for Stress Relief", "url": "<a href='https://www.youtube.com/watch?v=8TTABLdGCKI'>5-Minute Stress Buster | BOX BREATHING Exercises That Work | Saurabh Bothra</a>"},
                {"title": "Calming Meditation for Hormonal Balance", "url": "<a href='https://www.youtube.com/watch?v=5kjmGRhMq-o'>MEDITATION TO BALANCE HORMONES </a>"}
            ],
            "nutrition": [
                {"title": "Foods That Help Reduce Period Pain", "url": "<a href='https://www.youtube.com/watch?v=LIsYbDCMfDc'>Top 3 Ways to get Immediate Relief | Period cramp | Period Pain Relief | Home Remedies | Dr Hansaji</a>"},
                {"title": "Anti-Inflammatory Diet Tips for Menstrual Health", "url": "<a href='https://www.youtube.com/watch?v=E-8gvJlkY8c'>Avoid These Foods During Periods | Women's Health | Foods to Avoid in Menstruation</a>"},
                {"title": "Meal Prep Ideas for Period Week", "url": "<a href='https://www.youtube.com/watch?v=7nBR2e87Uso&t=32s'>healthy cook with me 🌿✨menstrual cycle meal prep | foods that help me feel better on my period</a>"}
            ],
            "exercise": [
                {"title": "Low-Impact Workouts Safe During Your Period", "url": "<a href='https://www.youtube.com/watch?v=gA8LEqRXAVk'>Can I Exercise on My Period?! (Dos & Don'ts) | Joanna Soh </a>"},
                {"title": "10-Minute Stretches for Menstrual Cramp Relief", "url": "<a href='https://www.youtube.com/watch?v=cXPRE_h4-nY'>10 Minute Gentle Yoga for PMS | Period Pain Relief</a>"},
                {"title": "Walking Meditation for Period Discomfort", "url": "<a href='https://www.youtube.com/watch?v=sxRXiEDHtRU'>Soothe Your Period Pain With This Guided Meditation By A Clinical Psychologist (PMS Meditation) </a>"}
            ],
            "selfcare": [
                {"title": "Creating a Period Self-Care Routine", "url": "<a href='https://www.youtube.com/watch?v=o0rFSOXuUMc'>Monthly Period Hygiene Tips I Follow That Worked Wonders! | Tips all girls need to know ✨🌷</a>"},
                {"title": "DIY Heat Therapy for Menstrual Cramps", "url": "<a href='https://www.youtube.com/watch?v=21HEr110HKg'>How to INSTANTLY Relieve Painful Periods (Menstrual Cramps) </a>"},
                {"title": "Relaxing Evening Routine for Better Sleep During Your Period", "url": "<a href='https://www.youtube.com/watch?v=Cjee6sM1Pow'>6 simple tips for better sleep during your period</a>"}
            ]
        }
        
        self.symptom_responses = {
            "cramp": [
                "Cramping can be challenging. Have you tried using a heating pad on your lower abdomen?",
                "For cramps, gentle stretching or light yoga might provide some relief.",
                "Some people find relief from cramps with over-the-counter pain relievers like ibuprofen."
            ],
            "mood": [
                "Mood swings are a common PMS symptom. Taking time for self-care activities you enjoy might help.",
                "For mood fluctuations, regular light exercise can help stabilize your emotions.",
                "Mood changes during your cycle are normal. Tracking them might help you prepare and cope better."
            ],
            "tired": [
                "Fatigue during your period is common. Make sure you're getting enough rest and staying hydrated.",
                "Iron-rich foods might help with period-related fatigue. Consider spinach, beans, or lean meats.",
                "Listen to your body - if you need extra rest during your period, that's completely normal."
            ],
            "headache": [
                "Period headaches can be related to hormonal changes. Staying hydrated might help reduce their intensity.",
                "For menstrual headaches, try applying a cold compress to your forehead or neck.",
                "Some find that reducing screen time helps with period-related headaches."
            ],
            "bloat": [
                "Bloating is a common symptom. Reducing salt intake and staying hydrated can help minimize it.",
                "Gentle abdominal massage might help relieve bloating discomfort.",
                "Some herbal teas like peppermint or ginger may help reduce bloating sensations."
            ],
            "back": [
                "Back pain during periods can be tough. Have you tried gentle stretching or yoga?",
                "A warm bath might help alleviate period-related back pain.",
                "Some find relief from back pain with gentle massage or a heating pad."
            ]
        }
        
        # Map symptoms to relevant video categories
        self.symptom_to_video_category = {
            "cramp": ["yoga", "selfcare"],
            "mood": ["meditation", "selfcare"],
            "tired": ["nutrition", "selfcare"],
            "headache": ["meditation", "selfcare"],
            "bloat": ["nutrition", "exercise"],
            "back": ["yoga", "exercise"]
        }
        
        self.greetings = ["hi", "hello", "hey", "greetings", "howdy", "hiya"]
        self.goodbyes = ["bye", "goodbye", "see you", "exit", "quit", "end"]
        
    def process_message(self, message):
        """Process user message and generate appropriate response"""
        user_input = message.lower().strip()
        end_session = False
        
        # First time user without a name
        if not self.user_name or self.user_name == "there":
            self.user_name = user_input.strip() if user_input.strip() else "there"
            return {
                "response": f"Nice to meet you, {self.user_name}! How can I help with period stress today? You can share symptoms you're experiencing or ask for general tips.",
                "end_session": False
            }
            
        # Check for video request
        if any(word in user_input for word in ["video", "youtube", "watch"]):
            response = self.suggest_videos(user_input)
            
        # Check for exit commands
        elif any(word in user_input for word in self.goodbyes):
            response = self.handle_goodbye()
            end_session = True
            
        # Check for greetings
        elif any(greeting in user_input for greeting in self.greetings) and len(user_input.split()) < 3:
            response = self.handle_greeting()
            
        # Check for specific symptoms mentioned
        elif found_symptoms := self.identify_symptoms(user_input):
            response = self.handle_symptoms(found_symptoms)
            
        # Check for questions
        elif "?" in user_input:
            response = self.handle_question(user_input)
            
        # Handle general requests for tips
        elif any(word in user_input for word in ["tip", "advice", "help", "suggest", "recommend"]):
            response = self.provide_random_tip()
            
        # Default response
        else:
            response = self.provide_default_response()
            
        return {
            "response": response,
            "end_session": end_session
        }
        
    def suggest_videos(self, query):
        """Suggest relevant YouTube videos based on user query"""
        responses = []
        
        # Determine which category of videos to suggest
        category = None
        
        if any(word in query for word in ["yoga", "stretch", "pose", "exercise"]):
            category = "yoga"
        elif any(word in query for word in ["meditate", "meditation", "breathing", "relax", "calm"]):
            category = "meditation"
        elif any(word in query for word in ["food", "eat", "diet", "nutrition"]):
            category = "nutrition"
        elif any(word in query for word in ["workout", "exercise", "activity", "move"]):
            category = "exercise"
        elif any(word in query for word in ["self care", "selfcare", "care", "routine"]):
            category = "selfcare"
        
        # If no specific category is identified, suggest random videos
        if not category:
            symptoms = self.identify_symptoms(query)
            if symptoms:
                # Get video categories related to the first symptom
                categories = self.symptom_to_video_category.get(symptoms[0], ["selfcare"])
                category = random.choice(categories)
            else:
                # If no symptoms found, pick a random category
                category = random.choice(list(self.youtube_suggestions.keys()))
                
        # Get videos from the selected category
        videos = self.youtube_suggestions[category]
        selected_videos = random.sample(videos, min(2, len(videos)))
        
        responses.append(f"Here are some videos that might help with {category}:")
        for video in selected_videos:
            responses.append(f"• {video['title']}: {video['url']}")
        
        responses.append("\nWould you like suggestions on a different topic? Or do you have specific symptoms to discuss?")
        
        return "\n".join(responses)
        
    def identify_symptoms(self, text):
        """Identify period-related symptoms in user text"""
        symptoms_found = []
        
        symptom_keywords = {
            "cramp": ["cramp", "pain", "ache", "hurt"],
            "mood": ["mood", "irritable", "angry", "sad", "emotional", "anxious", "anxiety", "depression"],
            "tired": ["tired", "fatigue", "exhausted", "no energy", "low energy"],
            "headache": ["headache", "migraine", "head pain", "head hurts"],
            "bloat": ["bloat", "bloated", "swollen", "puffy", "water retention"],
            "back": ["back pain", "backache", "sore back"]
        }
        
        for symptom, keywords in symptom_keywords.items():
            if any(keyword in text for keyword in keywords):
                symptoms_found.append(symptom)
                
        return symptoms_found
        
    def handle_symptoms(self, symptoms):
        """Respond to identified symptoms"""
        responses = []
        
        if len(symptoms) == 1:
            symptom = symptoms[0]
            response = random.choice(self.symptom_responses[symptom])
            responses.append(response)
            
            # Add video suggestion
            if symptom in self.symptom_to_video_category:
                video_category = random.choice(self.symptom_to_video_category[symptom])
                video = random.choice(self.youtube_suggestions[video_category])
                responses.append(f"\nYou might find this video helpful: {video['title']}: {video['url']}")
            
            responses.append("Would you like more specific advice for managing this symptom?")
        else:
            responses.append(f"I see you're experiencing several symptoms including {', '.join(symptoms)}.")
            responses.append("Let's address them one by one:")
            
            for symptom in symptoms[:2]:  # Limit to first 2 symptoms to avoid overwhelming
                response = random.choice(self.symptom_responses[symptom])
                responses.append(f"For {symptom}: {response}")
            
            # Add video suggestion for one of the symptoms
            primary_symptom = symptoms[0]
            if primary_symptom in self.symptom_to_video_category:
                video_category = random.choice(self.symptom_to_video_category[primary_symptom])
                video = random.choice(self.youtube_suggestions[video_category])
                responses.append(f"\nFor {primary_symptom}, this video might help: {video['title']}: {video['url']}")
                
            if len(symptoms) > 2:
                responses.append("Would you like advice for your other symptoms as well?")
                
        return "\n".join(responses)
        
    def handle_question(self, question):
        """Handle user questions"""
        responses = []
        
        # Period tracking questions
        if any(word in question for word in ["track", "app", "monitor"]):
            responses.append("Tracking your cycle can be very helpful! Many people use apps like Clue, Flo, or Eve.")
            responses.append("These can help you predict symptoms and prepare for them in advance.")
            
            # Add video suggestion for tracking
            video = random.choice(self.youtube_suggestions["selfcare"])
            responses.append(f"\nThis video might give you additional insights: {video['title']}: {video['url']}")
            
        # Pain management questions
        elif any(word in question for word in ["pain", "hurt", "cramp"]):
            responses.append("For period pain management, you might try:")
            responses.append("1. Over-the-counter pain relievers like ibuprofen")
            responses.append("2. Heat therapy with a heating pad or warm bath")
            responses.append("3. Gentle exercises like walking or stretching")
            
            # Add video suggestion for pain management
            video = random.choice(self.youtube_suggestions["yoga"])
            responses.append(f"\nThis yoga video focuses on pain relief: {video['title']}: {video['url']}")
            
            responses.append("If pain is severe or interferes with daily activities, it's worth consulting a healthcare provider.")
            
        # Dietary questions
        elif any(word in question for word in ["eat", "food", "diet", "nutrition"]):
            responses.append("Your diet can impact period symptoms. Some suggestions include:")
            responses.append("- Increasing iron-rich foods for energy (leafy greens, beans, lean meats)")
            responses.append("- Consuming calcium-rich foods to reduce cramps (yogurt, almonds)")
            responses.append("- Reducing salt, caffeine, and sugar which may worsen bloating and mood swings")
            responses.append("- Staying well-hydrated with water and herbal teas")
            
            # Add video suggestion for nutrition
            video = random.choice(self.youtube_suggestions["nutrition"])
            responses.append(f"\nHere's a helpful nutrition video: {video['title']}: {video['url']}")
            
        # Emotional/mental health
        elif any(word in question for word in ["mood", "emotion", "stress", "anxiety", "sad"]):
            responses.append("Period-related mood changes are common due to hormonal fluctuations.")
            responses.append("Some helpful strategies include:")
            responses.append("- Regular mindfulness or meditation practice")
            responses.append("- Gentle exercise like yoga or walking")
            responses.append("- Getting adequate rest and sleep")
            responses.append("- Being kind to yourself and acknowledging that these feelings are temporary")
            
            # Add video suggestion for emotional well-being
            video = random.choice(self.youtube_suggestions["meditation"])
            responses.append(f"\nThis meditation video might help with emotional balance: {video['title']}: {video['url']}")
            
        # Video-specific questions
        elif any(word in question for word in ["video", "youtube", "watch"]):
            responses.append("I'd be happy to suggest some helpful videos.")
            responses.append("What specific aspect of period management are you interested in? Yoga, meditation, nutrition, exercise, or general self-care?")
            
        # Other/general questions
        else:
            responses.append("That's a good question. While I can offer general information about period stress management,")
            responses.append("for personalized advice, it's best to consult with a healthcare provider.")
            
            # Add general video suggestion
            video_category = random.choice(list(self.youtube_suggestions.keys()))
            video = random.choice(self.youtube_suggestions[video_category])
            responses.append(f"\nIn the meantime, you might find this video helpful: {video['title']}: {video['url']}")
            
            responses.append("Would you like me to share some general stress reduction tips instead?")
            
        return "\n".join(responses)
            
    def handle_greeting(self):
        """Respond to user greetings"""
        greetings = [
            f"Hello again, {self.user_name}! How are you feeling today?",
            f"Hi there, {self.user_name}! How can I help with period stress today?",
            f"Hey {self.user_name}! What period-related concerns can I help with?"
        ]
        greeting = random.choice(greetings)
        
        # Occasionally suggest video content
        if random.random() < 0.3:  # 30% chance to suggest videos
            video_promo = "\n\nI can also suggest helpful YouTube videos for managing period symptoms. Just ask if you'd like to see some!"
            return greeting + video_promo
        
        return greeting
        
    def handle_goodbye(self):
        """Handle user exit"""
        goodbyes = [
            f"Take care, {self.user_name}! Remember to be gentle with yourself.",
            f"Goodbye, {self.user_name}. I hope you feel better soon!",
            f"Until next time, {self.user_name}. Wishing you comfort and wellness!"
        ]
        return random.choice(goodbyes)
        
    def provide_random_tip(self):
        """Provide a random stress management tip"""
        tip = random.choice(self.stress_tips)
        
        # Add a video suggestion 50% of the time
        if random.random() < 0.5:
            video_category = random.choice(list(self.youtube_suggestions.keys()))
            video = random.choice(self.youtube_suggestions[video_category])
            response = f"Here's a tip that might help: {tip}\n\nFor visual guidance, check out this video: {video['title']}: {video['url']}\n\nWould you like another tip or do you have specific symptoms to discuss?"
        else:
            response = f"Here's a tip that might help: {tip}\n\nWould you like another tip, a video suggestion, or do you have specific symptoms to discuss?"
        
        return response
        
    def provide_default_response(self):
        """Provide a default response when input doesn't match other categories"""
        responses = [
            f"I'm here to help with period stress, {self.user_name}. Could you tell me more about what you're experiencing?",
            "Would you like some general tips for managing period discomfort?",
            "I can provide advice on specific symptoms like cramping, mood changes, or fatigue. What are you experiencing?"
        ]
        
        response = random.choice(responses)
        
        # Add video suggestion prompt occasionally
        if random.random() < 0.3:  # 30% chance
            response += "\n\nI can also suggest helpful YouTube videos if you're interested."
            
        return response