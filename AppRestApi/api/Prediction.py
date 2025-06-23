import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_absolute_error, mean_squared_error
import seaborn as sns
import joblib  # For saving the model and scaler

# Function to generate synthetic dataset
def generate_menstrual_cycle_dataset(num_samples=1000):
    """
    Generate a synthetic dataset for menstrual cycle prediction
    
    Parameters:
    num_samples (int): Number of samples to generate
    
    Returns:
    pandas.DataFrame: Synthetic dataset of menstrual cycle parameters
    """
    np.random.seed(42)
    
    # Generate synthetic data
    data = {
        'age': np.random.normal(30, 5, num_samples),  # Mean 30, std 5
        'cycle_length': np.random.normal(28, 3, num_samples),  # Mean 28, std 3
        'period_length': np.random.normal(5, 1, num_samples),  # Mean 5, std 1
        'stress_level': np.random.uniform(0, 10, num_samples),  # 0-10 scale
        'previous_cycle_variation': np.random.normal(0, 2, num_samples),  # Variation in previous cycle
        'hormonal_changes': np.random.normal(0, 1, num_samples),  # Hormonal fluctuations
        'next_period_days': np.zeros(num_samples)  # Target variable to predict
    }
    
    # Create DataFrame
    df = pd.DataFrame(data)
    
    # Generate more realistic next period days based on other features
    df['next_period_days'] = (
        df['cycle_length'] + 
        df['previous_cycle_variation'] + 
        (df['stress_level'] / 3) - 
        (df['hormonal_changes'] * 0.5)
    ).round(0)
    
    # Ensure next_period_days is within reasonable range
    df['next_period_days'] = df['next_period_days'].clip(21, 35)
    
    return df

# Generate dataset
df = generate_menstrual_cycle_dataset(5000)

# Visualize the dataset
plt.figure(figsize=(15, 10))

# Correlation Heatmap
plt.subplot(2, 2, 1)
correlation_matrix = df.corr()
sns.heatmap(correlation_matrix, annot=True, cmap='coolwarm', linewidths=0.5)
plt.title('Feature Correlation Heatmap')

# Distribution of Next Period Days
plt.subplot(2, 2, 2)
df['next_period_days'].hist(bins=20)
plt.title('Distribution of Next Period Days')
plt.xlabel('Days Until Next Period')
plt.ylabel('Frequency')

# Scatter plot of Cycle Length vs Next Period Days
plt.subplot(2, 2, 3)
plt.scatter(df['cycle_length'], df['next_period_days'], alpha=0.5)
plt.title('Cycle Length vs Next Period Days')
plt.xlabel('Cycle Length')
plt.ylabel('Days Until Next Period')

# Box plot of Next Period Days by Stress Level
plt.subplot(2, 2, 4)
df['stress_level_binned'] = pd.cut(df['stress_level'], bins=5, labels=["Very Low", "Low", "Medium", "High", "Very High"])
df.boxplot(column='next_period_days', by='stress_level_binned')
plt.title('Next Period Days by Stress Level')
plt.tight_layout()

# Prepare data for machine learning
X = df.drop(['next_period_days', 'stress_level_binned'], axis=1)
y = df['next_period_days']

# Split the data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Scale the features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train Random Forest Regressor
rf_model = RandomForestRegressor(n_estimators=100, random_state=42)
rf_model.fit(X_train_scaled, y_train)

# Make predictions
y_pred = rf_model.predict(X_test_scaled)

# Evaluate the model
mae = mean_absolute_error(y_test, y_pred)
mse = mean_squared_error(y_test, y_pred)
rmse = np.sqrt(mse)
'''
print("Model Performance Metrics:")
print(f"Mean Absolute Error: {mae:.2f} days")
print(f"Root Mean Squared Error: {rmse:.2f} days")
'''
# Feature Importance
feature_importance = pd.DataFrame({
    'feature': X.columns,
    'importance': rf_model.feature_importances_
}).sort_values('importance', ascending=False)
'''
print("\nFeature Importance:")
print(feature_importance)
'''
# Save the model and scaler
joblib.dump(rf_model, 'period_prediction_model.pkl')
joblib.dump(scaler, 'period_prediction_scaler.pkl')

# Prediction Function - now properly using the fitted scaler
def predict_next_period(age, cycle_length, period_length, stress_level, 
                         previous_cycle_variation=0, hormonal_changes=0,
                         model_path='period_prediction_model.pkl',
                         scaler_path='period_prediction_scaler.pkl'):
    """
    Predict the number of days until the next period
    
    Parameters:
    - age: User's age
    - cycle_length: Average cycle length
    - period_length: Duration of period
    - stress_level: Stress level (0-10)
    - previous_cycle_variation: Variation in previous cycle
    - hormonal_changes: Hormonal fluctuation intensity
    - model_path: Path to saved model
    - scaler_path: Path to saved scaler
    
    Returns:
    Predicted days until next period
    """
    # Load the model and scaler
    try:
        global rf_model, scaler
        model = joblib.load(model_path)
        scaler = joblib.load(scaler_path)
    except:
        # If running in an environment where the model hasn't been saved yet,
        # use the global model and scaler
        
        model = rf_model
        
    # Create input array
    input_data = np.array([[
        age, cycle_length, period_length, stress_level, 
        previous_cycle_variation, hormonal_changes
    ]])
    
    # Scale the input
    input_scaled = scaler.transform(input_data)
    
    # Predict
    prediction = model.predict(input_scaled)[0]
    
    return round(prediction, 1)
'''
# Example predictions - using the already fitted scaler
print("\nExample Predictions:")
print("Prediction for 30-year-old with average parameters:")
print(predict_next_period(30, 28, 5, 5))

print("\nPrediction for high-stress scenario:")
print(predict_next_period(35, 30, 6, 9))

print("\nPrediction for low-stress scenario:")
print(predict_next_period(25, 27, 4, 2))
'''