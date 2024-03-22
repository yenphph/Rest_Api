const express = require('express');
const cors = require('cors');
const app = express();
const mongoose = require('mongoose');

const clothesroute = require('./Router/Clothes');

const morgan = require('morgan');
require('dotenv').config();

app.use(express.json());
app.use(cors());
app.use(morgan('common'));

app.use("/v1/clothes/", clothesroute);
//mobile
const apiMobile = require('./api');
app.use('/api', apiMobile);

mongoose.connect((process.env.MongoDB))
.then(() =>{
    console.log('Connected to MongoDB');
}).catch((error) =>{
    console.log('Error connecting', error);
});
app.listen(5000, () =>{
    console.log("Server is running");
})