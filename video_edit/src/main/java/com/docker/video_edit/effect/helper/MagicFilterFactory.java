package com.docker.video_edit.effect.helper;

import com.docker.video_edit.R;
import com.docker.video_edit.effect.filter.BarrelBlurEffect;
import com.docker.video_edit.effect.filter.BlackAndWhiteEffect;
import com.docker.video_edit.effect.filter.ContrastEffect;
import com.docker.video_edit.effect.filter.CrossProcessEffect;
import com.docker.video_edit.effect.filter.GammaEffect;
import com.docker.video_edit.effect.filter.GlBilateralFilter;
import com.docker.video_edit.effect.filter.GlBoxBlurFilter;
import com.docker.video_edit.effect.filter.GlBulgeDistortionFilter;
import com.docker.video_edit.effect.filter.GlCGAColorspaceFilter;
import com.docker.video_edit.effect.filter.GlGaussianBlurFilter;
import com.docker.video_edit.effect.filter.GlGrayScaleFilter;
import com.docker.video_edit.effect.filter.GlHazeFilter;
import com.docker.video_edit.effect.filter.GlInvertFilter;
import com.docker.video_edit.effect.filter.GlMonochromeFilter;
import com.docker.video_edit.effect.filter.GlSharpenFilter;
import com.docker.video_edit.effect.filter.GlSphereRefractionFilter;
import com.docker.video_edit.effect.filter.GlToneCurveFilter;
import com.docker.video_edit.effect.filter.GlVignetteFilter;
import com.docker.video_edit.effect.filter.HueEffect;
import com.docker.video_edit.effect.filter.OverlayEffect;
import com.docker.video_edit.effect.filter.PosterizeEffect;
import com.docker.video_edit.effect.filter.SepiaEffect;
import com.docker.video_edit.effect.filter.SketchEffect;
import com.docker.video_edit.effect.filter.TemperatureEffect;
import com.docker.video_edit.effect.filter.base.GlFilter;
import com.docker.video_edit.effect.utils.ConfigUtils;

public class MagicFilterFactory {

    /**
     * 视频滤镜效果
     * @return
     */
    public static GlFilter getFilter() {
        MagicFilterType filterType = ConfigUtils.getInstance().getMagicFilterType();
        return getFilter(filterType);
    }

    private static GlFilter getFilter(MagicFilterType filterType) {
        switch (filterType) {
            case NONE:
                return new GlFilter();
            case BILATERAL:
                return new GlBilateralFilter();
            case BOXBLUR:
                return new GlBoxBlurFilter();
            case BULGEDISTORTION:
                return new GlBulgeDistortionFilter();
            case CGACOLORSPACE:
                return new GlCGAColorspaceFilter();
            case GAUSSIANBLUR:
                return new GlGaussianBlurFilter();
            case GRAYSCALE:
                return new GlGrayScaleFilter();
            case HAZE:
                return new GlHazeFilter(0.5f, 0.3f);
            case INVERT:
                return new GlInvertFilter();
            case LUT:
                return new GlVignetteFilter();
            case MONOCHROME:
                return new GlMonochromeFilter();
            case SEPIA:
                return new SepiaEffect();
            case SHARPEN:
                return new GlSharpenFilter(3.f);
            case SPHEREREFRACTION:
                return new GlSphereRefractionFilter();
            case TONECURVE:
                return new GlToneCurveFilter();
            case VIGNETTE:
                return new GlVignetteFilter(0.5f, 0.5f, 0.2f, 0.85f);
            case BLACKANDWHITE:
                return new BlackAndWhiteEffect();
            case OVERLAY:
                return new OverlayEffect();
            case BARRELBLUR:
                return new BarrelBlurEffect();
            case POSTERIZE:
                return new PosterizeEffect();
            case CONTRAST:
                return new ContrastEffect();
            case GAMMA:
                return new GammaEffect();
            case CROSSPROCESS:
                return new CrossProcessEffect();
            case HUE:
                return new HueEffect();
            case TEMPERATURE:
                return new TemperatureEffect();
            case SKETCH:
                return new SketchEffect();
            default:
                return new GlFilter();
        }
    }

    public static int filterType2Name(MagicFilterType filterType) {
        switch (filterType) {
            case NONE:
                return R.string.filter_none;
            case INVERT:
                return R.string.filter_invert;
            case MONOCHROME:
                return R.string.filter_monochrome;
            case SEPIA:
                return R.string.filter_sepia;
            case GRAYSCALE:
                return R.string.filter_grayscale;
            case SHARPEN:
                return R.string.filter_sharpen;
            case SPHEREREFRACTION:
                return R.string.filter_sphererefraction;
            case BULGEDISTORTION:
                return R.string.filter_bulgedistortion;
            case CGACOLORSPACE:
                return R.string.filter_cgacolorspace;
            case HAZE:
                return R.string.filter_haze;
            case BILATERAL:
                return R.string.filter_bilateral;
            case TONECURVE:
                return R.string.filter_tonecurve;
            case VIGNETTE:
                return R.string.filter_vignette;
            case BLACKANDWHITE:
                return R.string.filter_blackandwhite;
            case OVERLAY:
                return R.string.filter_overlay;
            case BARRELBLUR:
                return R.string.filter_barrelblur;
            case POSTERIZE:
                return R.string.filter_posterize;
            case CONTRAST:
                return R.string.filter_contrast;
            case GAMMA:
                return R.string.filter_gamma;
            case CROSSPROCESS:
                return R.string.filter_crossprocess;
            case HUE:
                return R.string.filter_hue;
            case TEMPERATURE:
                return R.string.filter_temperature;
            case SKETCH:
                return R.string.filter_sketch;
            default:
                return R.string.filter_none;
        }
    }

    public static int filterType2Color(MagicFilterType filterType) {
        switch (filterType) {
            case NONE:
                return R.color.filter_category_greenish_dummy;

            default:
                return R.color.filter_category_greenish_normal;
        }
    }

    public static int filterType2Thumb(MagicFilterType filterType) {
        switch (filterType) {
            case NONE:
                return R.drawable.filter;
            case INVERT:
                return R.drawable.filter_invert;
            case SEPIA:
                return R.drawable.filter_sepia;
            case GRAYSCALE:
                return R.drawable.filter_grayscale;
            case CGACOLORSPACE:
                return R.drawable.filter_cgacolorspace;
            case BARRELBLUR:
                return R.drawable.filter_barrelblur;
            case BLACKANDWHITE:
                return R.drawable.filter_blackandwhite;
            case CONTRAST:
                return R.drawable.filter_contrast;
            case CROSSPROCESS:
                return R.drawable.filter_crossprocess;
            case GAMMA:
                return R.drawable.filter_gamma;
            case HUE:
                return R.drawable.filter_hue;
            case OVERLAY:
                return R.drawable.filter_overlay;
            case TEMPERATURE:
                return R.drawable.filter_temperature;
            default:
                return R.drawable.filter;
        }
    }
}
