var express = require('express');
var router = express.Router();

const Distributors = require('../models/distributor');

/* GET users listing. */
// router.get('/', function(req, res, next) {
//   res.send('respond with a resource');
// });
router.get('/get-list-distributor', async (req, res) => {
  try {
    // lấy danh sách theo thứ tự distributors mới nhất
    const data = await Distributors.find();
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
router.get('/search-distributor', async (req, res) => {
  try {
    const key = req.query.key

    const data = await Distributors.find({ name: { "$regex": key, "$options": "i" } })
      .sort({ createdAt: -1 });

    if (data) {
      res.json({
        "status": 200,
        "messenger": "Thành công",
        "data": data
      })
    } else {
      res.json({
        "status": 400,
        "messenger": "Lỗi, không thành công",
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
});
router.post('/add-distributor', async (req, res) => {
  try {
    const data = req.body;
    const newDistributors = new Distributors({
      name: data.name
    });
    const result = await newDistributors.save();
    if (result) {
      res.json({
        'status': 200,
        "data": result
      })
    } else {
      res.json({
        "status": 400,
        "data": []
      })
    }
  } catch (error) {

  }
});
router.delete('/del-distributor/:id', async (req, res) => {
  try {
    const { id } = req.params
    const result = await Distributors.findByIdAndDelete(id);
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
})
router.put('/update-distribute/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const data = req.body;
    const result = await Distributors.findByIdAndUpdate(id, { name: data.name })
    if (result) {
      res.json({
        "status": 200,
        "data": result
      })
    } else {
      res.json({
        "status": 400,
        "data": null
      })
    }
  } catch (error) {

  }
})
module.exports = router;
