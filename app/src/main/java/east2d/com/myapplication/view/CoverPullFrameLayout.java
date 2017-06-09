package east2d.com.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by leo on 2017/5/22.
 */

public class CoverPullFrameLayout extends FrameLayout{
//    private static final int a = 8388659;
//    private static final float b = 0.5F;
//    private static final int c = 600;
//    private static final int d = 750;
//    private int A;
//    private int B;
//    private VelocityTracker C;
//    private b D;
//    private int E;
//    private int F;
//    private dl G;
//    private dm e;
//    private int f;
//    private View g;
//    private int h;
//    private int i;
//    private int j;
//    private View k;
//    private int l;
//    private View m;
//    private int n;
//    private int o;
//    private boolean p;
//    private boolean q;
//    private Rect r = new Rect();
//    private Rect s = new Rect();
//    private boolean t;
//    private MotionEvent u;
//    private int v;
//    private float w;
//    private boolean x = false;
//    private a y;
//    private c z;
//
    public CoverPullFrameLayout(Context paramContext)
    {
        super(paramContext);
    }
//
//    public CoverPullFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
//    {
//        super(paramContext, paramAttributeSet);
//        a(paramContext, paramAttributeSet);
//    }
//
//    public CoverPullFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
//    {
//        super(paramContext, paramAttributeSet, paramInt);
//        a(paramContext, paramAttributeSet);
//    }
//
//    private void a(float paramFloat)
//    {
//        int i1 = 1;
//        int i2 = 0;
//        if ((paramFloat < 0.0F) && (this.e.p())) {
//            return;
//        }
//        int i3 = Math.abs(this.g.getBottom() - this.s.bottom);
//        int i4;
//        label52:
//        label72:
//        int i5;
//        boolean bool;
//        if (this.j == -1)
//        {
//            i4 = 0;
//            if ((i4 != 0) && (paramFloat > 0.0F)) {
//                break label228;
//            }
//            if (this.i != 0) {
//                break label230;
//            }
//            i1 = 0;
//            if ((i1 != 0) && (paramFloat > 0.0F)) {
//                break label242;
//            }
//            i5 = this.e.i() + (int)paramFloat;
//            bool = this.e.c(i5);
//            if (!bool) {
//                break label255;
//            }
//        }
//        for (;;)
//        {
//            int i6 = (int)(paramFloat * 0.5F + this.g.getBottom() - this.i);
//            if ((int)(paramFloat + this.k.getTop()) > i6) {
//                break;
//            }
//            this.e.a(i2);
//            int i7 = i2 - this.e.h();
//            float f1 = i7;
//            if (bool) {}
//            for (float f2 = this.s.top - this.g.getTop();; f2 = 0.5F * i7)
//            {
//                a(f1, f2);
//                return;
//                if (i3 >= this.j)
//                {
//                    i4 = i1;
//                    break label52;
//                }
//                i4 = 0;
//                break label52;
//                label228:
//                break;
//                label230:
//                if (i3 >= this.i) {
//                    break label72;
//                }
//                i1 = 0;
//                break label72;
//                label242:
//                break;
//            }
//            label255:
//            i2 = i5;
//        }
//    }
//
//    private void a(float paramFloat1, float paramFloat2)
//    {
//        if (paramFloat1 == 0.0F) {
//            return;
//        }
//        if (paramFloat2 < 0.0F) {
//            paramFloat2 = (float)(paramFloat2 - 0.5D);
//        }
//        for (;;)
//        {
//            if (paramFloat2 + this.g.getTop() < this.s.top) {
//                paramFloat2 = this.s.top - this.g.getTop();
//            }
//            this.g.offsetTopAndBottom((int)paramFloat2);
//            this.k.offsetTopAndBottom((int)paramFloat1);
//            invalidate();
//            if (this.y == null) {
//                break;
//            }
//            this.y.a(this.g, (int)paramFloat2, this.s.top, this.g.getTop());
//            return;
//            if (paramFloat2 <= 0.0F) {}
//        }
//    }
//
//    private void a(int paramInt)
//    {
//        if (!this.e.a()) {
//            this.D.a(0, paramInt);
//        }
//    }
//
//    private void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
//    {
//        int i1 = getChildCount();
//        int i2 = getPaddingLeftWithForeground();
//        int i3 = paramInt3 - paramInt1 - getPaddingRightWithForeground();
//        int i4 = getPaddingTopWithForeground();
//        int i5 = paramInt4 - paramInt2 - getPaddingBottomWithForeground();
//        int i6 = this.e.i();
//        int i7;
//        int i8;
//        if (this.k != null)
//        {
//            i7 = this.k.getId();
//            if (this.g == null) {
//                break label128;
//            }
//            i8 = this.g.getId();
//            label80:
//            if (i6 >= 0) {
//                break label281;
//            }
//        }
//        label91:
//        label128:
//        label281:
//        for (int i9 = 0;; i9 = i6)
//        {
//            int i10 = 0;
//            if (i10 < i1)
//            {
//                View localView = getChildAt(i10);
//                if (localView.getVisibility() == 8) {}
//                for (;;)
//                {
//                    i10++;
//                    break label91;
//                    i7 = 0;
//                    break;
//                    i8 = 0;
//                    break label80;
//                    if ((i7 == localView.getId()) || (i8 == localView.getId()))
//                    {
//                        int i11 = localView.getMeasuredWidth();
//                        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localView.getLayoutParams();
//                        int i12 = i2 + (i3 - i2 - i11) / 2 + localMarginLayoutParams.leftMargin - localMarginLayoutParams.rightMargin;
//                        if (i7 == localView.getId()) {}
//                        for (int i13 = i9;; i13 = (int)(0.5F * i9))
//                        {
//                            int i14 = i13 + (i4 + localMarginLayoutParams.topMargin);
//                            localView.layout(i12, i14, i12 + i11, i14 + localView.getMeasuredHeight());
//                            break;
//                        }
//                    }
//                    a(localView, i2, i4, i3, i5, false);
//                }
//            }
//            return;
//        }
//    }
//
//    private void a(Context paramContext, AttributeSet paramAttributeSet)
//    {
//        setOverScrollMode(2);
//        this.e = new dm();
//        this.D = new b(paramContext);
//        if (paramAttributeSet != null)
//        {
//            TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, b.o.PullHoverScrollView);
//            if (localTypedArray != null)
//            {
//                float f1 = localTypedArray.getFloat(TypedValue.TYPE_STRING, 0.6F);
//                this.e.a(f1);
//                this.h = localTypedArray.getResourceId(0, 0);
//                this.l = localTypedArray.getResourceId(1, 0);
//                this.n = localTypedArray.getResourceId(2, 0);
//                this.j = localTypedArray.getDimensionPixelSize(4, -1);
//                this.f = localTypedArray.getInt(5, 600);
//                localTypedArray.recycle();
//            }
//        }
//        ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
//        this.A = localViewConfiguration.getScaledTouchSlop();
//        this.E = localViewConfiguration.getScaledMinimumFlingVelocity();
//        this.F = localViewConfiguration.getScaledMaximumFlingVelocity();
//        this.B = this.A;
//    }
//
//    private void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
//    {
//        FrameLayout.LayoutParams localLayoutParams;
//        int i1;
//        int i2;
//        int i4;
//        label108:
//        int i7;
//        label117:
//        int i8;
//        if (paramView.getVisibility() != 8)
//        {
//            localLayoutParams = (FrameLayout.LayoutParams)paramView.getLayoutParams();
//            i1 = paramView.getMeasuredWidth();
//            i2 = paramView.getMeasuredHeight();
//            int i3 = localLayoutParams.gravity;
//            if (i3 == -1) {
//                i3 = 8388659;
//            }
//            if (Build.VERSION.SDK_INT < 17) {
//                break label180;
//            }
//            i4 = getLayoutDirection();
//            int i5 = Gravity.getAbsoluteGravity(i3, i4);
//            int i6 = i3 & 0x70;
//            switch (i5 & 0x7)
//            {
//                default:
//                    i7 = paramInt1 + localLayoutParams.leftMargin;
//                    switch (i6)
//                    {
//                        default:
//                            i8 = paramInt2 + localLayoutParams.topMargin;
//                    }
//                    break;
//            }
//        }
//        for (;;)
//        {
//            paramView.layout(i7, i8, i7 + i1, i8 + i2);
//            return;
//            label180:
//            i4 = 0;
//            break;
//            i7 = paramInt1 + (paramInt3 - paramInt1 - i1) / 2 + localLayoutParams.leftMargin - localLayoutParams.rightMargin;
//            break label117;
//            if (paramBoolean) {
//                break label108;
//            }
//            i7 = paramInt3 - i1 - localLayoutParams.rightMargin;
//            break label117;
//            i8 = paramInt2 + localLayoutParams.topMargin;
//            continue;
//            i8 = paramInt2 + (paramInt4 - paramInt2 - i2) / 2 + localLayoutParams.topMargin - localLayoutParams.bottomMargin;
//            continue;
//            i8 = paramInt4 - i2 - localLayoutParams.bottomMargin;
//        }
//    }
//
//    private boolean a(int paramInt1, int paramInt2)
//    {
//        View localView1 = this.m;
//        boolean bool = false;
//        if (localView1 != null)
//        {
//            View localView2 = this.m;
//            int i1 = localView2.getTop() - localView2.getScrollY();
//            bool = false;
//            if (paramInt2 >= i1)
//            {
//                int i2 = localView2.getBottom() - localView2.getScrollY();
//                bool = false;
//                if (paramInt2 < i2)
//                {
//                    int i3 = localView2.getLeft();
//                    bool = false;
//                    if (paramInt1 >= i3)
//                    {
//                        int i4 = localView2.getRight();
//                        bool = false;
//                        if (paramInt1 < i4) {
//                            bool = true;
//                        }
//                    }
//                }
//            }
//        }
//        return bool;
//    }
//
//    private boolean a(MotionEvent paramMotionEvent)
//    {
//        return super.dispatchTouchEvent(paramMotionEvent);
//    }
//
//    private boolean a(MotionEvent paramMotionEvent, int paramInt)
//    {
//        this.u = paramMotionEvent;
//        this.e.b(paramMotionEvent.getX(), paramMotionEvent.getY());
//        this.C.addMovement(paramMotionEvent);
//        float f1 = this.e.f();
//        float f2 = this.e.g();
//        this.w += Math.abs(f2);
//        boolean bool3;
//        if (this.q) {
//            bool3 = a(paramMotionEvent);
//        }
//        boolean bool4;
//        do
//        {
//            dl localdl;
//            do
//            {
//                int i2;
//                do
//                {
//                    return bool3;
//                    if ((Math.abs(f1) > this.A) && (Math.abs(f1) > Math.abs(f2))) {}
//                    for (boolean bool1 = true;; bool1 = false)
//                    {
//                        this.t = bool1;
//                        if ((!this.t) || (!this.e.p())) {
//                            break;
//                        }
//                        return a(paramMotionEvent);
//                    }
//                    if (((paramInt == 0) && (Math.abs(f2) < this.B)) || (this.w < this.A)) {
//                        return a(paramMotionEvent);
//                    }
//                    if ((f2 > 0.0F) && (!this.e.l())) {}
//                    for (int i1 = 1; (i1 != 0) && (this.G != null) && (!this.G.a(this, this.g, this.k)); i1 = 0) {
//                        return a(paramMotionEvent);
//                    }
//                    if (i1 == 0) {}
//                    for (i2 = 1;; i2 = 0)
//                    {
//                        boolean bool2 = this.e.k();
//                        if ((i1 == 0) && ((i2 == 0) || (!bool2))) {
//                            break;
//                        }
//                        a(f2);
//                        return true;
//                    }
//                    bool3 = false;
//                } while (i2 == 0);
//                localdl = this.G;
//                bool3 = false;
//            } while (localdl == null);
//            bool4 = this.G.a(this, this.k);
//            bool3 = false;
//        } while (!bool4);
//        b(f2);
//        return true;
//    }
//
//    private void b()
//    {
//        if (!this.e.a()) {
//            this.D.a(0, this.f);
//        }
//    }
//
//    private boolean b(float paramFloat)
//    {
//        int i1 = 65286;
//        if (paramFloat == 0.0F) {}
//        while ((paramFloat > 0.0F) && (this.e.p())) {
//            return false;
//        }
//        if (!this.p)
//        {
//            this.p = true;
//            if (this.z != null) {
//                this.z.t();
//            }
//        }
//        int i2 = this.e.i() + (int)paramFloat;
//        if (i2 < i1) {}
//        for (;;)
//        {
//            this.e.a(i1);
//            int i3 = i1 - this.e.h();
//            if (this.y != null) {
//                this.y.a(this.k, i3, this.k.getScrollY());
//            }
//            return true;
//            if (i2 > 0) {
//                i1 = 0;
//            } else {
//                i1 = i2;
//            }
//        }
//    }
//
//    private void c()
//    {
//        if (this.C == null)
//        {
//            this.C = VelocityTracker.obtain();
//            return;
//        }
//        this.C.clear();
//    }
//
//    private void d()
//    {
//        if (this.C != null)
//        {
//            this.C.recycle();
//            this.C = null;
//        }
//    }
//
//    private void e()
//    {
//        if (this.u == null) {
//            return;
//        }
//        MotionEvent localMotionEvent = this.u;
//        a(MotionEvent.obtain(localMotionEvent.getDownTime(), localMotionEvent.getEventTime() + ViewConfiguration.getLongPressTimeout(), 0, localMotionEvent.getX(), localMotionEvent.getY(), localMotionEvent.getMetaState()));
//    }
//
//    private void f()
//    {
//        if (this.u == null) {
//            return;
//        }
//        MotionEvent localMotionEvent = this.u;
//        a(MotionEvent.obtain(localMotionEvent.getDownTime(), localMotionEvent.getEventTime() + ViewConfiguration.getLongPressTimeout(), 3, localMotionEvent.getX(), localMotionEvent.getY(), localMotionEvent.getMetaState()));
//    }
//
//    private void g() {}
//
//    private int getPaddingBottomWithForeground()
//    {
//        return getPaddingBottom();
//    }
//
//    private int getPaddingLeftWithForeground()
//    {
//        return getPaddingLeft();
//    }
//
//    private int getPaddingRightWithForeground()
//    {
//        return getPaddingRight();
//    }
//
//    private int getPaddingTopWithForeground()
//    {
//        return getPaddingTop();
//    }
//
//    private void h() {}
//
//    public boolean a()
//    {
//        return b.b(this.D);
//    }
//
//    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
//    {
//        boolean bool = true;
//        if (this.k == null)
//        {
//            bool = super.dispatchTouchEvent(paramMotionEvent);
//            return bool;
//        }
//        int i1 = this.v;
//        int i2 = paramMotionEvent.getActionMasked();
//        this.v = i2;
//        switch (i2)
//        {
//            default:
//            case 0:
//            case 2:
//                do
//                {
//                    return super.dispatchTouchEvent(paramMotionEvent);
//                    this.x = false;
//                    this.e.a(paramMotionEvent.getX(), paramMotionEvent.getY());
//                    this.w = 0.0F;
//                    this.D.b();
//                    c();
//                    this.C.addMovement(paramMotionEvent);
//                    super.dispatchTouchEvent(paramMotionEvent);
//                    if ((!this.r.isEmpty()) && (!this.s.isEmpty())) {
//                        break;
//                    }
//                    this.r.set(this.k.getLeft(), this.k.getTop(), this.k.getRight(), this.k.getBottom());
//                    this.s.set(this.g.getLeft(), this.g.getTop(), this.g.getRight(), this.g.getBottom());
//                    return bool;
//                } while (!a(paramMotionEvent, i1));
//                return bool;
//        }
//        this.e.c();
//        d();
//        if ((this.e.k()) || (this.e.l()))
//        {
//            if (this.e.l()) {
//                a(750);
//            }
//            while (this.e.o())
//            {
//                f();
//                return bool;
//                b();
//            }
//            return a(paramMotionEvent);
//        }
//        return a(paramMotionEvent);
//    }
//
//    public int getHeadMaxMove()
//    {
//        return this.j;
//    }
//
//    protected void onDetachedFromWindow()
//    {
//        super.onDetachedFromWindow();
//        if (this.D != null) {
//            b.a(this.D);
//        }
//    }
//
//    protected void onFinishInflate()
//    {
//        super.onFinishInflate();
//        if ((this.h != 0) && (this.g == null))
//        {
//            this.g = findViewById(this.h);
//            if (this.g.getLayoutParams() != null) {
//                this.i = Math.abs(((FrameLayout.LayoutParams)this.g.getLayoutParams()).topMargin);
//            }
//        }
//        if ((this.l != 0) && (this.k == null)) {
//            this.k = findViewById(this.l);
//        }
//        if (this.n > 0)
//        {
//            this.m = findViewById(this.n);
//            this.o = ((FrameLayout.LayoutParams)this.m.getLayoutParams()).topMargin;
//        }
//    }
//
//    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
//    {
//        if (((paramMotionEvent.getActionMasked() == 2) && (this.t) && (a((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()))) || (a()) || ((this.G != null) && (this.G.a(paramMotionEvent)))) {
//            return true;
//        }
//        return super.onInterceptTouchEvent(paramMotionEvent);
//    }
//
//    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
//    {
//        a(paramInt1, paramInt2, paramInt3, paramInt4);
//    }
//
//    public void setDisablePull(boolean paramBoolean)
//    {
//        this.q = paramBoolean;
//    }
//
//    public void setHandler(dl paramdl)
//    {
//        this.G = paramdl;
//    }
//
//    public void setHeader(View paramView)
//    {
//        this.g = paramView;
//    }
//
//    public void setOnReboundListener(c paramc)
//    {
//        this.z = paramc;
//    }
//
//    public void setOnScrollListener(a parama)
//    {
//        this.y = parama;
//    }
//
//    public static abstract interface a
//    {
//        public abstract void a(View paramView, int paramInt1, int paramInt2);
//
//        public abstract void a(View paramView, int paramInt1, int paramInt2, int paramInt3);
//
//        public abstract void k_();
//    }
//
//    class b implements Runnable {
//        private static final int g = 0;
//        private static final int h = 1;
//        private int b;
//        private Scroller c;
//        private boolean d = false;
//        private int e;
//        private int f;
//        private int i = 0;
//
//        public b(Context paramContext)
//        {
//            this.c = new Scroller(paramContext);
//        }
//
//        private void a(boolean paramBoolean)
//        {
//            this.d = false;
//            this.b = 0;
//            CoverPullFrameLayout.this.removeCallbacks(this);
//            if (this.i == 1)
//            {
//                CoverPullFrameLayout.a(CoverPullFrameLayout.this, false);
//                if (CoverPullFrameLayout.a(CoverPullFrameLayout.this) != null) {
//                    CoverPullFrameLayout.a(CoverPullFrameLayout.this).c(paramBoolean);
//                }
//            }
//        }
//
//        private void c()
//        {
//            a(false);
//            CoverPullFrameLayout.b(CoverPullFrameLayout.this);
//        }
//
//        private void d()
//        {
//            a(true);
//            if (!this.c.isFinished()) {
//                this.c.forceFinished(true);
//            }
//        }
//
//        public void a(int paramInt1, int paramInt2)
//        {
//            if (CoverPullFrameLayout.d(CoverPullFrameLayout.this).b(paramInt1)) {
//                return;
//            }
//            this.e = CoverPullFrameLayout.d(CoverPullFrameLayout.this).j();
//            this.f = paramInt1;
//            int j = this.f - this.e;
//            if (j > 0) {}
//            for (this.i = 1;; this.i = 0)
//            {
//                this.b = 0;
//                CoverPullFrameLayout.this.removeCallbacks(this);
//                if (!this.c.isFinished()) {
//                    this.c.forceFinished(true);
//                }
//                this.c.startScroll(0, 0, 0, j, paramInt2);
//                CoverPullFrameLayout.this.post(this);
//                this.d = true;
//                return;
//            }
//        }
//
//        public boolean a()
//        {
//            return this.i == 1;
//        }
//
//        public void b()
//        {
//            if (this.d)
//            {
//                if (!this.c.isFinished()) {
//                    this.c.forceFinished(true);
//                }
//                CoverPullFrameLayout.c(CoverPullFrameLayout.this);
//                a(true);
//            }
//        }
//
//        public void run()
//        {
//            int j;
//            int m;
//            if ((!this.c.computeScrollOffset()) || (this.c.isFinished()))
//            {
//                j = 1;
//                int k = this.c.getCurrY();
//                m = k - this.b;
//                if (j != 0) {
//                    break label91;
//                }
//                this.b = k;
//                if (this.i != 1) {
//                    break label79;
//                }
//                CoverPullFrameLayout.a(CoverPullFrameLayout.this, m);
//            }
//            for (;;)
//            {
//                CoverPullFrameLayout.this.post(this);
//                return;
//                j = 0;
//                break;
//                label79:
//                CoverPullFrameLayout.b(CoverPullFrameLayout.this, m);
//            }
//            label91:
//            c();
//        }
//    }
//
//    public interface c
//    {
//        void c(boolean paramBoolean);
//
//        void t();
//    }
//
//    public interface dl
//    {
//          boolean a(MotionEvent paramMotionEvent);
//
//          boolean a(CoverPullFrameLayout paramPullHoverScrollView, View paramView);
//
//          boolean a(CoverPullFrameLayout paramPullHoverScrollView, View paramView1, View paramView2);
//    }
//
//    public class dm
//    {
//        public static final int a = 0;
//        public static final int b = -250;
//        public static final float c = 0.6F;
//        private PointF d = new PointF();
//        private float e;
//        private float f;
//        private int g = 0;
//        private int h = 0;
//        private int i = 0;
//        private float j = 0.6F;
//        private boolean k = false;
//        private int l = 0;
//
//        public void a(float paramFloat)
//        {
//            this.j = paramFloat;
//        }
//
//        public void a(float paramFloat1, float paramFloat2)
//        {
//            this.k = true;
//            this.i = this.g;
//            this.d.set(paramFloat1, paramFloat2);
//        }
//
//        protected void a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
//        {
//            c(paramFloat3, paramFloat4 * this.j);
//        }
//
//        public final void a(int paramInt)
//        {
//            this.h = this.g;
//            this.g = paramInt;
//            a(paramInt, this.h);
//        }
//
//        protected void a(int paramInt1, int paramInt2) {}
//
//        public boolean a()
//        {
//            return this.k;
//        }
//
//        public float b()
//        {
//            return this.j;
//        }
//
//        public final void b(float paramFloat1, float paramFloat2)
//        {
//            a(paramFloat1, paramFloat2, paramFloat1 - this.d.x, paramFloat2 - this.d.y);
//            this.d.set(paramFloat1, paramFloat2);
//        }
//
//        public boolean b(int paramInt)
//        {
//            return this.g == paramInt;
//        }
//
//        public void c()
//        {
//            this.k = false;
//        }
//
//        protected void c(float paramFloat1, float paramFloat2)
//        {
//            this.e = paramFloat1;
//            this.f = paramFloat2;
//        }
//
//        public boolean c(int paramInt)
//        {
//            return paramInt < 0;
//        }
//
//        public void d()
//        {
//            this.l = this.g;
//        }
//
//        public boolean e()
//        {
//            return this.g >= this.l;
//        }
//
//        public float f()
//        {
//            return this.e;
//        }
//
//        public float g()
//        {
//            return this.f;
//        }
//
//        public int h()
//        {
//            return this.h;
//        }
//
//        public int i()
//        {
//            return this.g;
//        }
//
//        public int j()
//        {
//            return this.g;
//        }
//
//        public boolean k()
//        {
//            return this.g > 0;
//        }
//
//        public boolean l()
//        {
//            return this.g < 0;
//        }
//
//        public boolean m()
//        {
//            return (this.h == 0) && (k());
//        }
//
//        public boolean n()
//        {
//            return (this.h != 0) && (p());
//        }
//
//        public boolean o()
//        {
//            return this.g != this.i;
//        }
//
//        public boolean p()
//        {
//            return this.g == 0;
//        }
//    }


}

