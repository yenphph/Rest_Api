var express = require('express');
var router = express.Router();

const Clothes = require('../models/clothes');
const Upload = require('../config/common/upload');
router.get('/clothes', async (req, res) => {
  try {
    //lay ds theo thu tu distributors
    const data = await Clothes.find();
    if (data) {
      res.json({
        "status": 200,
        "data": data
      })
    } else {
      res.json({
        "status": 400,
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
router.post('/clothes', Upload.array('image', 5), async (req, res) => {
  //Upload.array('image',5) => up nhiều file tối đa là 5
  //upload.single('image') => up load 1 file
  try {
    const data = req.body;//lay du lieu tu body
    const { files } = req;//files nếu upload nhiều, file nếu upload 1 file
    const urlsImage = files.map((file) => `${req.protocol}://${req.get("host")}/uploads/${file.filename}`)
    const newClothe = new Clothes({
      name: data.name,
      description: data.description,
      // them url
      image: urlsImage,
      price: data.price
    });
    const result = await newClothe.save();
    if (result) {
      res.json({
        "status": 200,
        "data": result
      })
    } else {
      res.json({
        "status": 400,
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
});
router.put("/clothes/:id", Upload.array('image', 5), async (req, res) => {
  try {
    const { id } = req.params
    const data = req.body;
    const { files } = req;
    const urlsImage =
      files.map((file) => `${req.protocol}://${req.get('host')}/uploads/${file.filename}`);
    const updateclothes = await Clothes.findById(id);
    files.map(345, updateclothes.image);
    let result = null;
    if (updateclothes) {
      updateclothes.name = data.name ?? updateclothes.name,
        updateclothes.description = data.description ?? updateclothes.description,
        updateclothes.price = data.price ?? updateclothes.price,
        updateclothes.image = urlsImage ?? updateclothes.image,
        result = await updateclothes.save();
    }
    if (result) {
      res.json({
        'status': 200,
        'data': result
      })
    } else {
      res.json({
        'status': 400,
        'data': []
      })
    }
  } catch (error) {
    console.log(error);
  }
});
 

router.delete('/clothes/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const result = await Clothes.findByIdAndDelete(id);
    if (result) {
      res.json({
        "status": 200,
        "data": result
      })
    } else {
      res.json({
        "status": 400,
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
});
router.get('/search', async (req, res) => {
  try {
    const key = req.query.key

    const data = await Clothes.find({ name: { "$regex": key, "$options": "i" } })
      .sort({ createdAt: -1 });

    if (data) {
      res.json({
        "status": 200,
       
        "data": data
      })
    } else {
      res.json({
        "status": 400,
       
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
});

module.exports = router;
