ALTER TABLE questions 
DROP COLUMN sentiment,
ADD COLUMN sentiment_analysis BOOLEAN DEFAULT false; 