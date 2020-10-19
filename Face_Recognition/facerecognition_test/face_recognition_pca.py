import os
import keras
from keras.preprocessing import image
from keras.applications.imagenet_utils import preprocess_input
from keras.models import Model
import numpy as np
import json
import matplotlib.pyplot
from matplotlib.pyplot import imshow
from PIL import Image
from sklearn.decomposition import PCA
import random
from sklearn.manifold import TSNE
import pickle
import traceback
import pickle

class FaceRecongnitionPCA:

    def __init__(self):
        self.model = keras.applications.VGG16(weights='imagenet', include_top=True)
        self.feat_extractor = Model(inputs=self.model.input, outputs=self.model.get_layer("fc2").output)

    def run(self):
        path = "../datas/output/"
        files = os.listdir(path)
        images = []
        train = []
        for idx, filename in enumerate(files):
            file_path = path + filename
            images.append(file_path)
            train.append(self.load_image(file_path))

        features = []
        for image_array in train:
            feat = self.feat_extractor.predict(image_array)[0]
            features.append(feat)


        pca, pcaFeatures = self.getImagePCA(3, features)
        print(pca)
        print(pcaFeatures)

        result = self.saveModelFile(images, pca, pcaFeatures, features, "test")

    """
           PCA 주성분 분석 및 차원 축소
       """
    def load_image(self, filename):
        img = image.load_img(filename, target_size=(224, 224))
        img_array = image.img_to_array(img)
        img = np.expand_dims(img_array, axis=0)
        img = preprocess_input(img)
        return img

    def getImagePCA(self, count, features):
        features = np.array(features)

        # 4096 차원 -> 차원 변경
        pca = PCA(n_components=count)
        pca.fit(features)
        pcaFeatures = pca.transform(features)

        return pca, pcaFeatures
    """
        모델 저장. pca model, tsneModel
    """
    def saveModelFile(self, images, pca, pcaFeatures, features, fileName):
        pcaModel = fileName + ".p"
        try:
            #pca
            pickle.dump([images, pcaFeatures, pca, features], open(pcaModel, 'wb'))

            # tsne
            self.create_tsne(images, pcaFeatures, fileName)
        except:
            print(traceback.format_exc())
            return False, ""
        return True

    def create_tsne(self, images, pca_features, fileName):
        try:

            # 최대 결과 이미지 1000개
            num_images_to_plot = 1000

            # 이미지 갯수가 1000개가 넘으면 랜덤으로 추출.
            if len(images) > num_images_to_plot:
                sort_order = sorted(random.sample(range(len(images)), num_images_to_plot))
                images = [images[i] for i in sort_order]
                pca_features = [pca_features[i] for i in sort_order]

            X = np.array(pca_features)
            tsne = TSNE(n_components=2, learning_rate=150, perplexity=30, angle=0.2, verbose=2).fit_transform(X)

            tx, ty = tsne[:, 0], tsne[:, 1]
            tx = (tx - np.min(tx)) / (np.max(tx) - np.min(tx))
            ty = (ty - np.min(ty)) / (np.max(ty) - np.min(ty))

            width = 4000
            height = 3000
            max_dim = 100

            full_image = Image.new('RGBA', (width, height))
            for img, x, y in zip(images, tx, ty):
                tile = Image.open(img)
                rs = max(1, tile.width / max_dim, tile.height / max_dim)
                tile = tile.resize((int(tile.width / rs), int(tile.height / rs)), Image.ANTIALIAS)
                full_image.paste(tile, (int((width - max_dim) * x), int((height - max_dim) * y)),
                                 mask=tile.convert('RGBA'))

            matplotlib.pyplot.figure(figsize=(16, 12))
            imshow(full_image)

            # 모델별 영역.
            full_image.save(fileName + ".png")

            tsne_path = fileName + ".json"

            data = [{"path": os.path.abspath(img), "point": [float(x), float(y)]} for img, x, y in zip(images, tx, ty)]
            with open(tsne_path, 'w') as outfile:
                json.dump(data, outfile)

            print("saved t-SNE result to %s" % tsne_path)
            return True
        except:
            print(traceback.format_exc())
            return False
if __name__ == "__main__":
    start = FaceRecongnitionPCA()
    start.run()
