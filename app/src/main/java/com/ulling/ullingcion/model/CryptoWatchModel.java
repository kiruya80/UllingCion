package com.ulling.ullingcion.model;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcUtil;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CandlesLine;
import com.ulling.ullingcion.entites.Cryptowat.CandlesResult;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.network.RetrofitCryptowatService;
import com.ulling.ullingcion.util.Utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DatModel — 데이터 소스를 추상화합니다.
 * <p>
 * DataModel은 데이터를 이벤트 스트림을 통해서 소비 가능하게(consumable) 노출시킵니다. RxJava의 Observable 을 이용해서 말이죠.
 * 그것은 네트워크 계층이나 데이터베이스 또는 shared preferences 등의 다양한 소스로 부터 데이터를 구성합니다.
 * 그리고 쉽게 소비가능한 데이터를 누구든지 필요한 것들에 노출시킵니다. DataModel은 모든 비지니스 로직을 가지고 있게 됩니다.
 * <p>
 * 단일 책임 법칙(single responsibility principle)에 대해서 우리가 강조하는 것은 나중에 DataModel을 만들도록 이끌 것입니다.
 * 예를들어, 출력값을 API 서비스와 데이터베이스 계층으로부터 받아와 구성하는 ArticleDataModel이 있다고 합시다.
 * 이 DataModel은 age filter를 적용하여 최근의 뉴스들이 데이터베이스로부터 받아지도록 하기 위해서 비지니스 로직을 다루게 됩니다.
 */
public class CryptoWatchModel {
    private static CryptoWatchModel sInstance;

    private MutableLiveData<CryptowatSummary> cryptowatSummary = null;
    private MutableLiveData<CryptoWatchCandles> cryptoWatchCandles = null;
    private MutableLiveData<List<Candles>> candles = null;
    private MutableLiveData<List<CandlesLine>> candlesSupportLine = null;

    public CryptoWatchModel() {
        super();
    }

    public static CryptoWatchModel getInstance() {
        if (sInstance == null) {
            synchronized (CryptoWatchModel.class) {
                if (sInstance == null) {
                    sInstance = new CryptoWatchModel();
                }
            }
        }
        return sInstance;
    }

    public MutableLiveData<List<CandlesLine>> getCandlesSupportLine() {
        if (candlesSupportLine == null) {
            candlesSupportLine = new MutableLiveData<List<CandlesLine>>();
            List<CandlesLine> mCandlesAverage = new ArrayList<CandlesLine>();
            candlesSupportLine.postValue(mCandlesAverage);
        } else {
            return candlesSupportLine;
        }
        return candlesSupportLine;
    }

    public void setCandlesSupportLine(int supportCount, double minPrice) {
        if (candles != null) {
            new GetCandlesLineTask(supportCount, minPrice).execute();
        }
    }

    public MutableLiveData<CryptowatSummary> getSummary() {
        if (cryptowatSummary == null) {
            cryptowatSummary = new MutableLiveData<CryptowatSummary>();
        } else {
            return cryptowatSummary;
        }
        return cryptowatSummary;
    }

    public MutableLiveData<CryptoWatchCandles> getCandlesStick() {
        if (cryptoWatchCandles == null) {
            cryptoWatchCandles = new MutableLiveData<CryptoWatchCandles>();
        } else {
            return cryptoWatchCandles;
        }
        return cryptoWatchCandles;
    }

    public MutableLiveData<List<Candles>> getCandles() {
        if (candles == null) {
            candles = new MutableLiveData<List<Candles>>();
        } else {
            return candles;
        }
        return candles;
    }

    public void loadSummary() {
        QcLog.e("loadSummary ===  ");
        Call<CryptowatSummary> call = RetrofitCryptowatService.getInstance().getSummary();
        call.enqueue(new Callback<CryptowatSummary>() {
            @Override
            public void onResponse(Call<CryptowatSummary> call, Response<CryptowatSummary> response) {
                if (response.isSuccessful()) {
                    QcLog.e("onResponse === isSuccessful ");
                    CryptowatSummary result = response.body();
                    cryptowatSummary.postValue(result);
                } else {
                    QcLog.e("onResponse === false");
                    cryptowatSummary = new MutableLiveData<CryptowatSummary>();
                }
            }

            @Override
            public void onFailure(Call<CryptowatSummary> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString() + " , " + t.getMessage());
                cryptowatSummary = new MutableLiveData<CryptowatSummary>();
            }
        });
    }

    public void loadCandlesStick(long after, final int periods) {
        QcLog.e("loadCandlesStick ===  " + periods + " , after = " + after);
        Call<CryptoWatchCandles> call = RetrofitCryptowatService.getInstance().getCandlesStick(after, periods);
        call.enqueue(new Callback<CryptoWatchCandles>() {
            @Override
            public void onResponse(Call<CryptoWatchCandles> call, Response<CryptoWatchCandles> response) {
                if (response.isSuccessful()) {
                    QcLog.e("onResponse === isSuccessful ");
                    CryptoWatchCandles result = response.body();
                    cryptoWatchCandles.postValue(result);

                    if (result != null && result.getResult() != null) {
                        new GetCandlesTask(periods, result.getResult()).execute();
                    } else {
                        QcLog.e("getCandles result == null == ");
                    }
                } else {
                    QcLog.e("onResponse === false");
                    cryptoWatchCandles = new MutableLiveData<CryptoWatchCandles>();
                }
            }

            @Override
            public void onFailure(Call<CryptoWatchCandles> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString() + " , " + t.getMessage());

                cryptoWatchCandles = new MutableLiveData<CryptoWatchCandles>();
            }
        });
    }

    private class GetCandlesTask extends AsyncTask<Void, Void, Void> {
        private int periods;
        private CandlesResult result;
        private ArrayList<Candles> newCandles = new ArrayList<>();
//        private ArrayList<CandlesLine> newCandlesSupportLine = new ArrayList<>();

        public GetCandlesTask(int periods, CandlesResult result) {
            this.periods = periods;
            this.result = result;

            newCandles = new ArrayList<>();
//            newCandlesSupportLine = new ArrayList<>();
//            priceTotal = new ArrayList<Double>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            QcLog.e("GetCandlesTask === doInBackground ");
            newCandles = sortByCloseTime(getCandles(periods, result), false);
            rsi(newCandles);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            QcLog.e("GetCandlesTask === onPostExecute ");
            candles.postValue(newCandles);
        }
    }

    public static ArrayList<Candles> sortByCloseTime(ArrayList<Candles> oldList, final boolean asc) {
        if (oldList != null)
            Collections.sort(oldList, new Comparator<Candles>() {
                @Override
                public int compare(Candles candles1, Candles candles2) {
                    BigDecimal bd1 = new BigDecimal(String.valueOf(candles1.getCloseTime() / 10000));
                    BigDecimal bd2 = new BigDecimal(String.valueOf(candles2.getCloseTime() / 10000));
                    if (asc) {
//                    return (int) (candles1.getCloseTime() - candles2.getCloseTime());
                        BigDecimal mBigDecimal = bd1.subtract(bd2);
                        return mBigDecimal.intValue();
                    } else {
//                    return (int) (candles2.getCloseTime() - candles1.getCloseTime());
                        BigDecimal mBigDecimal = bd2.subtract(bd1);
                        return mBigDecimal.intValue();
                    }
                }
            });
        return oldList;
    }

    public ArrayList<Candles> getCandles(int periods, CandlesResult result) {
        QcLog.e("GetCandlesTask === getCandles ");
        ArrayList<Candles> newCandles = new ArrayList<>();

        if (periods == Define.VALUE_CRYPTOWAT_1M) {
            newCandles = setCandles(result.getCandles_1M());

        } else if (periods == Define.VALUE_CRYPTOWAT_3M) {
            newCandles = setCandles(result.getCandles_3M());

        } else if (periods == Define.VALUE_CRYPTOWAT_5M) {
            newCandles = setCandles(result.getCandles_5M());

        } else if (periods == Define.VALUE_CRYPTOWAT_15M) {
            newCandles = setCandles(result.getCandles_15M());

        } else if (periods == Define.VALUE_CRYPTOWAT_30M) {
            newCandles = setCandles(result.getCandles_30M());

        } else if (periods == Define.VALUE_CRYPTOWAT_1H) {
            newCandles = setCandles(result.getCandles_1H());

        } else if (periods == Define.VALUE_CRYPTOWAT_2H) {
            newCandles = setCandles(result.getCandles_2H());

        } else if (periods == Define.VALUE_CRYPTOWAT_4H) {
            newCandles = setCandles(result.getCandles_4H());

        } else if (periods == Define.VALUE_CRYPTOWAT_6H) {
            newCandles = setCandles(result.getCandles_6H());

        } else if (periods == Define.VALUE_CRYPTOWAT_12H) {
            newCandles = setCandles(result.getCandles_12H());

        } else if (periods == Define.VALUE_CRYPTOWAT_1D) {
            newCandles = setCandles(result.getCandles_1D());

        } else if (periods == Define.VALUE_CRYPTOWAT_3D) {
            newCandles = setCandles(result.getCandles_3D());

        } else if (periods == Define.VALUE_CRYPTOWAT_1W) {
            newCandles = setCandles(result.getCandles_1W());
        }
        return newCandles;
    }

    public ArrayList<Candles> setCandles(List<List<String>> candleList) {
        QcLog.e("GetCandlesTask === setCandles ");
        ArrayList<Candles> newCandles = new ArrayList<>();
        if (candleList == null)
            return newCandles;

        for (int i = 0; i < candleList.size(); i++) {
            long closeTime = Long.parseLong(candleList.get(i).get(0)) * 1000;

            Double OpenPrice = Double.parseDouble(candleList.get(i).get(1));
            Double HighPrice = Double.parseDouble(candleList.get(i).get(2));
            Double LowPrice = Double.parseDouble(candleList.get(i).get(3));
            Double ClosePrice = Double.parseDouble(candleList.get(i).get(4));
            Double Volume = Double.parseDouble(candleList.get(i).get(5));


            Candles mCandles = new Candles(closeTime,
                    OpenPrice,
                    HighPrice,
                    LowPrice,
                    ClosePrice,
                    Volume);
//            Candles mCandles = new Candles(closeTime,
//                    Utils.getRound(OpenPrice, Utils.NUMBER_TEN),
//                    Utils.getRound(HighPrice, Utils.NUMBER_TEN),
//                    Utils.getRound(LowPrice, Utils.NUMBER_TEN),
//                    Utils.getRound(ClosePrice, Utils.NUMBER_TEN),
//                    Volume);

            newCandles.add(mCandles);
        }
        return newCandles;
    }


    private class GetCandlesLineTask extends AsyncTask<Void, Void, Void> {
        private List<Candles> mCandles = new ArrayList<>();
        private ArrayList<Double> priceTotal;
        private ArrayList<CandlesLine> newCandlesSupportLine;
        private int supportCount = 2;
        private double minPrice = 4000;

        public GetCandlesLineTask(int supportCount, double minPrice) {
            this.supportCount = supportCount;
            this.minPrice = minPrice;
            this.priceTotal = new ArrayList<Double>();
            this.newCandlesSupportLine = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            QcLog.e("GetCandlesTask === doInBackground ");

            mCandles = candles.getValue();
            if (mCandles == null)
                return null;

            for (int i = 0; i < mCandles.size(); i++) {
                // 지지선 구하기
                if (mCandles.get(i).getOpenPrice() > mCandles.get(i).getClosePrice()) {
                    // 음봉
                    if (mCandles.get(i).getClosePrice() > 0)
                        priceTotal.add(mCandles.get(i).getClosePrice());
                    if (mCandles.get(i).getLowPrice() > 0)
                        priceTotal.add(mCandles.get(i).getLowPrice());
                } else {
                    if (mCandles.get(i).getOpenPrice() > 0)
                        priceTotal.add(mCandles.get(i).getOpenPrice());
                    if (mCandles.get(i).getLowPrice() > 0)
                        priceTotal.add(mCandles.get(i).getLowPrice());
                }

                // 저항선 구하기
//            if (mCandles.getOpenPrice() > mCandles.getClosePrice()) {
//                // 음봉
//                if (mCandles.getOpenPrice() > 0)
//                    priceTotal.add(mCandles.getOpenPrice());
//                if (mCandles.getHighPrice() > 0)
//                    priceTotal.add(mCandles.getHighPrice());
//            } else {
//                if (mCandles.getClosePrice() > 0)
//                    priceTotal.add(mCandles.getClosePrice());
//                if (mCandles.getHighPrice() > 0)
//                    priceTotal.add(mCandles.getHighPrice());
//            }

                // 전체
//            if (mCandles.getOpenPrice() > 0)
//                priceTotal.add(mCandles.getOpenPrice());
//            if (mCandles.getClosePrice() > 0)
//                priceTotal.add(mCandles.getClosePrice());
//            if (mCandles.getHighPrice() > 0)
//                priceTotal.add(mCandles.getHighPrice());
//            if (mCandles.getLowPrice() > 0)
//                priceTotal.add(mCandles.getLowPrice());

            }


            if (priceTotal.size() > 2)
                newCandlesSupportLine = getPriceStatistic(priceTotal, supportCount, minPrice);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            QcLog.e("GetCandlesTask === onPostExecute ");
            if (priceTotal.size() > 2)
                candlesSupportLine.postValue(newCandlesSupportLine);
        }
    }

    public ArrayList<CandlesLine> getPriceStatistic(ArrayList<Double> priceTotal, int supportCount, double minPrice) {
        QcLog.e("GetCandlesTask === getPriceStatistic ");
        ArrayList<CandlesLine> mCandlesAverageList = new ArrayList<>();
        List<Double> priceTotal_ = sortByPrice(priceTotal);
        int count = 1;

        for (int i = 0; i < priceTotal_.size(); i++) {
            if (i > 0) {
                CandlesLine mCandlesAverage = new CandlesLine();
                if (Double.compare(priceTotal_.get(i), priceTotal_.get(i - 1)) == 1) {
                    // 다른경우
                    // 달라진 경우만 이전 가격과 증감을 넣는다
                    if (count > supportCount && priceTotal_.get(i - 1) > minPrice) {
                        mCandlesAverage.setPrice(priceTotal_.get(i - 1));
                        mCandlesAverage.setCount(count);
                        mCandlesAverageList.add(mCandlesAverage);
                    }
                    count = 1;

                } else {
                    // 같은 경우
                    count++;
                }
            }
        }
        return mCandlesAverageList;
    }

    public List<Double> sortByPrice(List<Double> oldList) {
        QcLog.e("GetCandlesTask === sortByPrice ");
        Collections.sort(oldList, new Comparator<Double>() {
            @Override
            public int compare(Double price1, Double price2) {
//                return (int) (price1 - price2);

                BigDecimal bd1 = new BigDecimal(String.valueOf(price1));
                BigDecimal bd2 = new BigDecimal(String.valueOf(price2));
                BigDecimal mBigDecimal = bd1.subtract(bd2);
                return mBigDecimal.intValue();
            }
        });
        return oldList;
    }

    List<Candles> candlesList = new ArrayList<Candles>();

    private void rsi(List<Candles> candlesList_) {
//        gainSum = 0;
//        lossSum = 0;
//        this.candlesList = candlesList_;
//        if (candlesList != null) {
//            QcLog.e("rsi =================== " + candlesList.size());
//            for (int i = 0; i < candlesList.size(); i++) {
//                getRsi(i);
//            }
//
//            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
//            simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+9"));
//
//            for (int i = 0; i < candlesList.size(); i++) {
//                Date date = new Date(candlesList.get(i).getCloseTime());
//                String formattedDate = simpleDate.format(date);
//                QcLog.e("rsi ======= " + formattedDate + " , " + candlesList.get(i).getOpenPrice() +
//                        " , " + candlesList.get(i).calculations.getRsi());
//            }
//        }

    }

    //
//    public class Intervals {
//        Calculations calculations;
//
//        public class Calculations {
//            public int rs = 0;
//            public int rsi = 0;
//            public double rsAvgGain = 0;
//            public double rsAvgLoss = 0;
//            public double change = 0;
//        }
//
//    }
//
////    Intervals[] intervals;
    int defaultPeriods = 14;
    double gainSum = 0;
    double lossSum = 0;

    private void getRsi(int t) {
        QcLog.e("getRsi 1111 ===== " + t + " ===== " + candlesList.get(t).toString());
        if (0 == t) {
            this.candlesList.get(t).calculations.change = 0;
            this.candlesList.get(t).calculations.rs = 0;
            this.candlesList.get(t).calculations.rsi = 0;
            return;
        }

        int n = defaultPeriods;
        candlesList.get(t).calculations.setChange(QcUtil.GetDoubleSubtract(candlesList.get(t).getClosePrice(), candlesList.get(t).getOpenPrice()).doubleValue());

//        if (defaultPeriods == t) {
            if (t <= n) {
            if (candlesList.get(t).calculations.change >= 0) {
                // 양봉
                gainSum = QcUtil.GetDoubleAdd(gainSum, candlesList.get(t).calculations.change).doubleValue();
            } else {
                lossSum = QcUtil.GetDoubleAdd(lossSum, (-candlesList.get(t).calculations.change)).doubleValue();
            }
                QcLog.e("getRsi calculations 333 ===== " + gainSum + " , " + lossSum);
            candlesList.get(t).calculations.setRsAvgGain(QcUtil.GetDoubleDivide(gainSum, n).doubleValue());
            candlesList.get(t).calculations.setRsAvgLoss(QcUtil.GetDoubleDivide(lossSum, n).doubleValue());
                QcLog.e("getRsi calculations 333 ===== " + t + " ===== " + candlesList.get(t).calculations.toString());
            return;

        } else if (t > n) {
            Candles.Calculations a = this.candlesList.get(t - 1).calculations;
            if (this.candlesList.get(t).calculations.change > 0) {
                double rsAvgLoss_ = QcUtil.GetDoubleMultiply(a.rsAvgLoss, (n - 1)).doubleValue();
                this.candlesList.get(t).calculations.rsAvgLoss = QcUtil.GetDoubleDivide(rsAvgLoss_, n).doubleValue();

                double rsAvgGain_ = QcUtil.GetDoubleMultiply(a.rsAvgGain, (n - 1)).doubleValue();
                if (candlesList.get(t).calculations.change >= 0) {
                    double rsAvgGainAdd = QcUtil.GetDoubleAdd(rsAvgGain_, this.candlesList.get(t).calculations.change).doubleValue();
                    this.candlesList.get(t).calculations.rsAvgGain = QcUtil.GetDoubleDivide(rsAvgGainAdd, n).doubleValue();
                } else {
                    double rsAvgGainAdd = QcUtil.GetDoubleAdd(rsAvgGain_, (-this.candlesList.get(t).calculations.change)).doubleValue();
                    this.candlesList.get(t).calculations.rsAvgGain = QcUtil.GetDoubleDivide(rsAvgGainAdd, n).doubleValue();
                }


            } else {
                if (candlesList.get(t).calculations.change >= 0) {
                    double rsAvgLoss_ = QcUtil.GetDoubleMultiply(a.rsAvgLoss, (n - 1)).doubleValue();
                    double rsAvgLossAdd = QcUtil.GetDoubleAdd(rsAvgLoss_, this.candlesList.get(t).calculations.change).doubleValue();
                    this.candlesList.get(t).calculations.rsAvgLoss = QcUtil.GetDoubleDivide(rsAvgLossAdd, n).doubleValue();

                } else {
                    double rsAvgLoss_ = QcUtil.GetDoubleMultiply(a.rsAvgLoss, (n - 1)).doubleValue();
                    double rsAvgLossAdd = QcUtil.GetDoubleAdd(rsAvgLoss_, (-this.candlesList.get(t).calculations.change)).doubleValue();
                    this.candlesList.get(t).calculations.rsAvgLoss = QcUtil.GetDoubleDivide(rsAvgLossAdd, n).doubleValue();

                }
                double rsAvgGain_ = QcUtil.GetDoubleMultiply(a.rsAvgGain, (n - 1)).doubleValue();
                this.candlesList.get(t).calculations.rsAvgGain = QcUtil.GetDoubleDivide(rsAvgGain_, n).doubleValue();
            }


            if (this.candlesList.get(t).calculations.rsAvgLoss == 0) {
                this.candlesList.get(t).calculations.rsi = 100;
            } else {
                this.candlesList.get(t).calculations.rs = QcUtil.GetDoubleDivide(this.candlesList.get(t).calculations.rsAvgGain, this.candlesList.get(t).calculations.rsAvgLoss).doubleValue();

                double rsAdd = QcUtil.GetDoubleAdd(1, this.candlesList.get(t).calculations.rs).doubleValue();
                double rsDivide = QcUtil.GetDoubleDivide(100, rsAdd).doubleValue();
                this.candlesList.get(t).calculations.rsi = QcUtil.GetDoubleSubtract(100, rsDivide).doubleValue();
            }
            QcLog.e("getRsi calculations 333 ===== " + t + " ===== " + candlesList.get(t).calculations.toString());
        }

    }
}
