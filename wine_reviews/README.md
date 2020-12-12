# Wine Reviews Scala Final Project
CSYE7200 Final Project  
Team 6:
* Fangqing Wu 001305642
* Xinzhu Huang 001744806
* Yangyang Zhang 001342955  

---

1. The dataset consisting of 2 csv file is included in the repository 'src/main/resources'. 
2. Main functions:    
    * SQL Analysis -- 'Analysis/SQLAnalysis' (not finished yet)    
    * TF-IDF & RF Models for points prediction from description   
    * K-Means Models for recommendation by input features    
    * ! Some functions are realised from Play Framework (another directory 'wine_play')  
    
3. Main functions are in 'src/main/scala':   
    1. 'Wine' for Data import or reload from generated-processed csv files  
    2. 'Overview' to get description and might get a unique-value-counting function (need to undo the annotations)   
    3. 'Preprocessing', drop na, and related to 'WriteCSV'   
    
4. Main functions are in 'src/main/scala':   
    1. 'Run' to call functions in 'TFIDF_RFModel'
    2. 'SQLAnalysis'
    3. 'KMeansModels'
   
5. Other supportings: 
    Generated data files (csv & json) in 'generatedData'
    ! Some data files due to a too-large size have been removed, which also could been done again  