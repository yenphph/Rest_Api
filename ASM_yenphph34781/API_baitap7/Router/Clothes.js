const clothesController = require('../Controller/Clothes');
const upload = require('../config/common/upload');
const router = require('express').Router();
//lấy hêt danh sach
router.post("/", clothesController.getClothes);
//add
router.post('/add-fruit-with-file-image', upload.array('image', 5), (req, res) => clothesController.image.addFruitWithFileImage(req, res));
module.exports = router;