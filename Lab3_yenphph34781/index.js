const express = require('express');
const cors = require('cors');
const app = express();
const mongoose = require('mongoose');
const morgan = require('morgan');
const FruitRoute = require('./Router/Fruits');
const distributes = require('./Router/distributor');
require('dotenv').config();

app.use(express.json());
app.use(cors());
app.use(morgan('common'));

app.use("/v1/truit/", FruitRoute);
app.use("/v1/distributes/", distributes);

mongoose.connect((process.env.MongoDB))
  .then(() => {
    console.log('Connected to MongoDB');
  })
  .catch((error) => {
    console.log('Error connecting', error);
  });

app.listen(4000, () => {
  console.log("Server is running");
})

 