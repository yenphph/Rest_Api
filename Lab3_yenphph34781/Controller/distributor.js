const distributor = require('../Model/distributor');
const distributes = {
    addDistributors: async (req, res) => {
        try {
            const newDistributes = new distributor(req.body);
            const saveDistributor = await newDistributes.save();
            // res.status(200).json(saveDistributor);
            if (saveDistributor) {
                res.json({
                    "status": 200,
                    "messenger": "Them thanh cong",
                    "data": saveDistributor
                })
            } else {
                res.json({
                    "status": 400,
                    "messenger": "Loi, them khong thanh cong",
                    "data": []
                })
            }
        } catch (error) {
            res.status(500).json(error);
        }
    }
}
module.exports = distributes