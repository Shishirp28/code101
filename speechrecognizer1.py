import speech_recognition as sr
sample_rate=10000
chunk_size=4096 
r = sr.Recognizer()

mic_list= sr.Microphone.list_microphone_names()
device_id=0
with sr.Microphone(device_index = device_id , sample_rate= sample_rate,chunk_size= chunk_size) as source:
    r.adjust_for_ambient_noise(source)
    print("say something")
    audio= r.listen(source)
    
    try:
        text=r.recognize_google(audio)  
        
        fo=open(r"C:\Users\2015s\Downloads\studentfile","w")
        print("you said : "+text)
        fo.write(text)
        fo.close()
    except sr.UnknownValueError:       
        print("google speech recognition could not understand audio")
    except sr.RequestError as e:       
        print("could not request result from google".format(e))

import nltk.classify.util
from nltk.classify import NaiveBayesClassifier
from nltk.corpus import names
def word_feats(words):
    return dict([(word,True) for word in words])
positive_vocab=['awesome','outstanding','fantastic','terrific','nice','great',':)','good','very good','is','not bad']
negative_vocab=['bad','terrible','useless','hate',':(','not','hell','very bad','not good']
neutral_vocab=['movie','the','sound','was','is','actors','did','know','words','not','done','doing']

positive_features=[(word_feats(pos),'pos') for pos in positive_vocab]
negative_features=[(word_feats(neg),'neg') for neg in negative_vocab]
neutral_features=[(word_feats(neu),'neu') for neu in neutral_vocab]

train_set=negative_features+positive_features+ neutral_features
classifier= NaiveBayesClassifier.train(train_set)

neg=0
pos=0
neu=0
s="could not review"
fo=open(r"C:\Users\2015s\Downloads\studentfile","r")
s=fo.read()
sentence=s
words=sentence.split(' ')
for word in words:
    classResult=classifier.classify(word_feats(word))
    if classResult =='neg':
        neg=neg+1
    if classResult =='pos':
        pos=pos+1
    if classResult =='neu':
        neu=neu+1
    
        
         
    
print('Review:')
print ('Positive:'+ str(float(pos)/len(words)))
print ('Negative:'+str(float(neg)/len(words)))
