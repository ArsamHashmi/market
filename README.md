# market
crypto market tracker: Based on requirements provided in document.

- Market Activity : Consists of manually inserted (random) cryptocurrencies in listview with dummy prices.
                    All prices are hardcoded and the % market change is changed randomly after x seconds (specified in code).
                    %change Textview background turns red and green incase of market depreciation and appreciation respectively. 
- Graph Activity :  Activity Contains candle chart which reflects real market values instead of picking randomly generated values 
                    from previous screen.
                    All values are picked by consuming coinmarketcap API (using Retrofit)
                    
TODOs:
- Working on Activity Lifecycles
- Service to update values in real-time
- Code Optimization
- Commenting and cleaning code 
- Testing
